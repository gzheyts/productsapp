# ProductsApp
![Screenshot from running application](.scrot/productsapp.png?raw=true "Screenshot ProductsApp")

## About

This demo project was bootstrapped with [create-react-app], [spring-boot-v2] and also [jhipster].

Try deployed instance with Heroku [productsapp](https://productsapp-demo.herokuapp.com)

## Goals

The main goal is to integrate various technologies and principles in a single full-stack application.

## Dependencies
### Backend
 + [mapstruct]
 + [jjwt]
 + [spring-security]
 + [spring-data-jpa]
 + [liquibase]
 + ...

### Frontend

 + [axios]
 + [reactstrap]
 + [jsonwebtoken]
 + [redux]
 + [redux-thunk]
 + [redux-devtools]
 + [react-fontawesome]
 + [react-bootstrap-table2]
 + [react-bootstrap-table2-overlay]
 + [react-bootstrap-table2-paginator]
 + [react-bootstrap-typeahead]


## Overview
Clone project with 

```
    git clone github.com/gzheyts/productsapp <project_root>
```

### Running application

```bash
mvn -f <project_root>/pom.xml spring-boot:run 
```
Then navigate to [localhost:8080](http://localhost:8080)

### Running API

```bash
mvn -f <project_root>/pom.xml spring-boot:run -Dskip.yarn
```
This will run `api` on [localhost:8080/api](http://localhost:8080/api)

### Running Frontend dev server
```bash
cd <project_root>/src/main/webapp
yarn start
```
Will run frontend dev server at [localhost:3000](http://localhost:3000)

### Running server-side tests

To launch application's tests, run:
```
mvn -f <project_root>/pom.xml test -Dskip.yarn
``` 
## API usage examples [httpie]

### Register user request

```bash
http POST :8080/users/register username="user" password="password"
```
response
```
HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 0
Expires: 0
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: SAMEORIGIN
X-XSS-Protection: 1; mode=block
```

### Obtain JWT auth token for registered user
```bash
http POST :8080/login username="user" password="password"
```
response 
```
HTTP/1.1 200 
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzMxOTgxNTYsInN1YiI6InVzZXIifQ.zvw99k1_Nh1UTNI6ZCBHL3v6-boXvuiyBNEcFM2LVhW0Rc6pAN7lEEJpXcSsOrJy0_9ifzgcJvCJTE9C1hW-ig
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Length: 0
Expires: 0
Pragma: no-cache
X-Content-Type-Options: nosniff
X-Frame-Options: SAMEORIGIN
X-XSS-Protection: 1; mode=block
```

### Access protected resources
```bash
http :8080/api/categories Authorization:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzMxOTgxNTYsInN1YiI6InVzZXIifQ.zvw99k1_Nh1UTNI6ZCBHL3v6-boXvuiyBNEcFM2LVhW0Rc6pAN7lEEJpXcSsOrJy0_9ifzgcJvCJTE9C1hW-ig"
```
response
```

HTTP/1.1 200 
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Expires: 0
Pragma: no-cache
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: SAMEORIGIN
X-XSS-Protection: 1; mode=block

{
    "content": [... ],
    "page": 0,
    "pageSize": 20,
    "total": 5
}
```

### Logout
```bash
http :8080/logout Authorization:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzMxOTgxNTYsInN1YiI6InVzZXIifQ.zvw99k1_Nh1UTNI6ZCBHL3v6-boXvuiyBNEcFM2LVhW0Rc6pAN7lEEJpXcSsOrJy0_9ifzgcJvCJTE9C1hW-ig"
```

## Copyright and license

The code is released under the *MIT license*

[spring-boot-v2]: https://spring.io/projects/spring-boot
[create-react-app]: https://github.com/facebook/create-react-app
[httpie]: https://httpie.org/
[mapstruct]:https://github.com/mapstruct 
[jjwt]:https://github.com/jwtk/jjwt
[spring-security]: https://github.com/spring-projects/spring-security
[spring-data-jpa]: https://projects.spring.io/spring-data-jpa
[liquibase]: https://github.com/liquibase/liquibase

[jhipster]: https://github.com/jhipster/generator-jhipster

[axios]:https://github.com/axios/axios
[reactstrap]:https://github.com/reactstrap/reactstrap
[jsonwebtoken]:https://github.com/DefinitelyTyped/DefinitelyTyped
[redux]: https://github.com/reduxjs/redux
[redux-thunk]: https://github.com/reduxjs/redux-thunk
[redux-devtools]: https://github.com/reduxjs/redux-devtools
[react-fontawesome]: https://github.com/FortAwesome/react-fontawesome
[react-bootstrap-table2]:https://github.com/react-bootstrap-table/react-bootstrap-table2
[react-bootstrap-table2-overlay]:https://github.com/react-bootstrap-table/react-bootstrap-table2/tree/master/packages/react-bootstrap-table2-overlay
[react-bootstrap-table2-paginator]:https://github.com/react-bootstrap-table/react-bootstrap-table2/tree/master/packages/react-bootstrap-table2-paginator
[react-bootstrap-typeahead]:https://github.com/ericgio/react-bootstrap-typeahead
