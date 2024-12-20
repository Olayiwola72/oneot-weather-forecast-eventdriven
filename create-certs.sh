set -o nounset \
    -o errexit

# Load the .env file
if [ -f .env ]; then
    source .env
else
    echo ".env file not found! Exiting."
    exit 1
fi

# Assign SSL_SECRET to PASSWORD
if [ -z "${SSL_SECRET:-}" ]; then
    echo "SSL_SECRET is not set in .env file! Exiting."
    exit 1
fi
PASSWORD=$SSL_SECRET

printf "Deleting previous (if any)..."
rm -rf secrets
mkdir secrets
mkdir -p tmp
echo " OK!"

# Generate CA key
printf "Creating CA..."
openssl req -new -x509 -keyout tmp/datahub-ca.key -out tmp/datahub-ca.crt -days 365 -subj '/CN=ca.datahub/OU=test/O=datahub/L=paris/C=fr' -passin pass:$PASSWORD -passout pass:$PASSWORD >/dev/null 2>&1

echo " OK!"

for i in 'producer' 'consumer' 'schema-registry' 'kafka' 'control-center' 'zookeeper' 'elasticsearch'
do
	printf "Creating cert and keystore of $i..."
	# Create keystores
	keytool -genkey -noprompt \
				 -alias $i \
				 -dname "CN=$i, OU=test, O=datahub, L=paris, C=fr" \
				 -keystore secrets/$i.keystore.jks \
				 -keyalg RSA \
				 -storepass "$PASSWORD" \
				 -keypass "$PASSWORD"  >/dev/null 2>&1

	# Create CSR, sign the key, and import back into keystore
	keytool -keystore secrets/$i.keystore.jks -alias $i -certreq -file tmp/$i.csr -storepass "$PASSWORD" -keypass "$PASSWORD" >/dev/null 2>&1

	openssl x509 -req -CA tmp/datahub-ca.crt -CAkey tmp/datahub-ca.key -in tmp/$i.csr -out tmp/$i-ca-signed.crt -days 365 -CAcreateserial -passin pass:"$PASSWORD" >/dev/null 2>&1

	keytool -keystore secrets/$i.keystore.jks -alias CARoot -import -noprompt -file tmp/datahub-ca.crt -storepass "$PASSWORD" -keypass "$PASSWORD" >/dev/null 2>&1

	keytool -keystore secrets/$i.keystore.jks -alias $i -import -file tmp/$i-ca-signed.crt -storepass "$PASSWORD" -keypass "$PASSWORD" >/dev/null 2>&1

	# Create truststore and import the CA cert
	keytool -keystore secrets/$i.truststore.jks -alias CARoot -import -noprompt -file tmp/datahub-ca.crt -storepass "$PASSWORD" -keypass "$PASSWORD" >/dev/null 2>&1
  echo " OK!"
done

echo "$PASSWORD" > secrets/cert_creds
rm -rf tmp

echo "SUCCEEDED"
