## How to start auth server

**Make sure you installed Docker in your local machine**.

Under this folder, please run

```sh
docker-compose up -d keycloak
```

## Set up users for testing

Login to `localhost:8080` with admin/admin.

Based on this article you can setup 2 clients for testing.

After setting up, there should be 2 clients as following.

| client_id | client_secret             | scope |
| --------- | ------------------------- | ----- |
| client-admin   | check it in your keycloak | ADMIN |
| client-user   | check it in your keycloak | USER  |

**We will use this info to fetch assessToken and then call movie related APIs.**

## Making calls to fetch token

```sh
curl --location --request POST 'http://localhost:8080/auth/realms/axa/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=client-admin' \
--data-urlencode 'client_secret=da52e65e-a202-4f67-aea8-09a89ef5ab8d' \
--data-urlencode 'grant_type=client_credentials'
```

## Examples of response body

```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJyLUZybkE0N2Y1aHlLeTJGTlA2ZTBiM3hlUC14dm1jb3BpX0p2WG92bFFvIn0.eyJleHAiOjE2MzcyNzk0ODAsImlhdCI6MTYzNzI0MzQ4MCwianRpIjoiYTEwODY5ZjctYTE5Ny00NDVlLTllODQtOWI2MGUxYjQ5NGJhIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2F4YSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJlN2U4ZWFkYi0xYjE4LTQ4OTItYWVkZi03OWJkN2U2OTJlYWEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQtYWRtaW4iLCJzZXNzaW9uX3N0YXRlIjoiYWFjODQ5ZWMtOWQyNi00YTY3LWFjOTItNWYzMjJkZWM5NWUyIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImNsaWVudC1hZG1pbiI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiIsIkFETUlOX1JPTEUiLCJST0xFX0FETUlOIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIEFETUlOIHByb2ZpbGUiLCJjbGllbnRJZCI6ImNsaWVudC1hZG1pbiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjE3Mi4yMC4wLjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtY2xpZW50LWFkbWluIiwiY2xpZW50QWRkcmVzcyI6IjE3Mi4yMC4wLjEifQ.lZWQE_wnoBC6_UYIC6k1cioF5lV5DJvchyrzJVNzJA6j91C24ft7IXDjRDWknFw1UvH7EKmJpkQhL9hBpDIQa02SnT7XmD9uXKn3hRIXqmKkZKMyIKe7xyP6ru81Gh8VU5-G15k9nJJXpmlu4rrOffN2Xn_cnmhjXbt1xhBAaop0UGffzpCeQ6ZX1zbbs4z-O8IG5A72V7s0rJHOcxqhwJhlEHrkKp8X_k1AnHCKbDjQgAdmPLy_JdD4tiSnygoNJYfbLZqBsb72bB-2fi02j1VzgOhiYNZsiYTCgMxIK8hKk5joOky9q8jy9IRA3nWPmHtbtKVTga8G6JUGN_8w3w",
  "expires_in": 35999,
  "refresh_expires_in": 1799,
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3ZmVkZTdhYS1jNDhiLTQ3NzktOTMyMi1iYmU5YTA3OWE2OTQifQ.eyJleHAiOjE2MzcyNDUyODAsImlhdCI6MTYzNzI0MzQ4MCwianRpIjoiZDE5OGY4NTItNzY1OC00M2IyLTg4YTQtNDc3YWVjYTI3ZTc2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2F4YSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hdXRoL3JlYWxtcy9heGEiLCJzdWIiOiJlN2U4ZWFkYi0xYjE4LTQ4OTItYWVkZi03OWJkN2U2OTJlYWEiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY2xpZW50LWFkbWluIiwic2Vzc2lvbl9zdGF0ZSI6ImFhYzg0OWVjLTlkMjYtNGE2Ny1hYzkyLTVmMzIyZGVjOTVlMiIsInNjb3BlIjoiZW1haWwgQURNSU4gcHJvZmlsZSJ9.ax83IRpIKyhPfpeDbhEcInuSJ2ctVcHSimnmhfArR1Y",
  "token_type": "bearer",
  "not-before-policy": 0,
  "session_state": "aac849ec-9d26-4a67-ac92-5f322dec95e2",
  "scope": "email ADMIN profile"
}
```
