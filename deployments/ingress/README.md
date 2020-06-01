# Create a namespace for your ingress resources
kubectl create namespace ingress-basic

# deploy an NGINX ingress controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-0.32.0/deploy/static/provider/cloud/deploy.yaml

# generate selfsigned certificate with private key
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -out aks-ingress-tls.crt \
    -keyout aks-ingress-tls.key \
    -subj "/CN=david.azure.com/O=aks-ingress-tls"
    
# set it as secret
kubectl create secret tls aks-ingress-tls \
    --namespace ingress-basic \
    --key aks-ingress-tls.key \
    --cert aks-ingress-tls.crt    
    
    
curl -v -k --resolve david.azure.com:443:51.124.85.52 https://david.azure.com/informer