### Taco Cloud Authorization Server

This app is just OAuth2 authorization server for the
for usage in taco-cloud app. For now it is populated with some dummy users data
to test the basic functionality of oauth2 auth server.

* OAuth2 authorization server:

1. Point Your web browser to:

```
http://localhost:9000/oauth2/authorize?response_type=code&client_id=oidc-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/oidc-client&scope=openid+profile+createIngredients+deleteIngredients
```

2. After logging in you’ll be asked to consent to the
   requested scopes on a page:
   example: [ ] deleteIngredients, [ ] createIngredients
3. After granting consent, the browser will be redirected back to the client URL. There is
   no client yet, so there’s nothing there and you’ll receive an error.
   But that’s OK - for now just get the authorization `code` from the URL.
4. Exchange authorization code for an access token:

example: (replace $code with retrieved code from url)

``` 
curl http://localhost:9000/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=authorization_code" \
  -d "redirect_uri=http://127.0.0.1:9090/login/oauth2/code/oidc-client" \
  -d "code=$code" \
  -u oidc-client:secret

```

5. Use access_token (it's valid for 299 seconds!) for example:

```
   curl localhost:8080/ingredients \
   -H"Content-type: application/json" \
   -H"Authorization: Bearer $acess_token" \
   -d'{"id":"FISH","name":"Stinky Fish", "type":"PROTEIN"}'
```


