# REST Best Practices
At this point we're going to talk about...

[Replace this logo] ![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Introduction](#introduction)
* [URL construction](#url-construction)
* [Operations over resources](#operations-over-resources)
* [Status codes](#status-codes)
* [Payload formatting](#payload-formatting)
* [Filters](#filters)
* [Pagination](#pagination)
* [HATEOAS](#hateoas)
* [API Versioning](#api-versioning)
* [API throughput restrictions](#api-throughput-restrictions)
* [OAuth](#oauth)
* [Errors](#errors)
* [Status and Health endpoints](#status-and-health-endpoints)
* [References](#references)

## Introduction
---
In this guide we are going to describe the best practices we consider most relevant at design time for a good REST API.

According with the wikipedia definition:
* *Representational State Transfer (REST)* ***is the software architectural style*** *of the World Wide Web. REST gives a coordinated set of constraints to the design of components in a distributed hypermedia system that can lead to a higher-performing and more maintainable architecture.* [[1](#rest_wikipedia)]

In the above definition, we can see that REST is a architectural style not an implementation. An implementation of this architecture is ***RESTFul***. This is a common mistake that some people have. 

The use of REST is often preferred over the more heavyweight SOAP (Simple Object Access Protocol) style because REST does not leverage as much bandwidth, which makes it a better fit for use over the Internet. 

REST, which typically runs over HTTP (Hypertext Transfer Protocol), has several architectural constraints:

1. Decouples consumers from producers

2. Stateless existence

3. Able to leverage a cache

4. Leverages a layered system

5. Leverages a uniform interface

The REST style emphasizes that interactions between clients and services is enhanced by having a limited number of operations (verbs). Flexibility is provided by assigning resources (nouns) their own unique Universal Resource Identifiers (URIs). Because each verb has a specific meaning (GET, POST, PUT and DELETE), REST avoids ambiguity.

The use of hypermedia both for application information as to the state transitions of the application: the representation of this state in a REST system are typically HTML, XML or JSON. As a result, it is possible to browse a resource REST to many others simply following links without requiring the use of registries or another additional infrastructure.

## URL Construction
---

## Operations over resources
---

The operations over resources are limited, the variety is on the resources. The operations over REST are specified as the standard HTTP methods, it constraints the construction of operations trough these methods.

There are operations that are **idempotent**, it means that it can be called many times without different outcomes. It would not matter if the method is called only once, or ten times over, the system state will be the same.

Moreover, there are **safe operations**, which should never change the resource. These operations could be cached without any consequence to the resource.

### Basic operations
In a REST implementation there are four basic operations which can be published for a resource.

#### GET

This operation retrieves all the information of a resource, or all resources in a collection (if the resource is a collection). It's a safe operation and it should have not other effect.

```
GET /clients/123

```


#### POST

It introduces an item in the collection represented by the resource. It is used to create a *new* item in the collection, the URI of the final resource will be defined by the server. 

```
POST /products
{ "name": "car", "color": "black" }
```


#### PUT

PUT operation requests that the entity is stored in the resource indicated. It means that the resource doesn't exist, it creates it. However, if the resource exists, it is overwritten by the given entity. Because of this behavoir, it is idempotent. 

POST and PUT are similar, POST will be used when we don't know the locality of the resource, and PUT where we know it. For this reason, POST is usually implemented as create operation while PUT can be used as update.

```
PUT /products/123
{ "name": "car", "color": "black" }
```


#### DELETE

It deletes the specified resource. Despite the server could return other response if the item already was deleted (the resource does not exist), this operation is idempotent, because the system status will be the same.

```
DELETE /client/123

```


### Extra operations

These are the basic operations, they allow to implement CRUD operations, but there are some extra operations. It can be used in some special requirements.

**HEAD** operation is similar to GET, with the difference that with HEAD operation the data retrieved only includes the header. Normally it is used if the size of content of the resources is large.

All the operations doesn't have to be implemented, with **OPTIONS** operation the client can discover the list of methods implemented for a resource.

The HTTP methods **PATCH** can be used to update partial resources. While PUT operation must take a full resource representation as the request entity (if only few attributes are provided, the others should be removed), PATCH operation allow partial changes to a resource. It is not idempotent. This operation should not be used as a partial data which only will be updated, it should define the 'suboperation' that are going to do over the resource.

```
PATCH /clients/123
[
    { "op": "replace", "path": "/name", "value": "Patricia" }
]
```



## Status codes
---

## Payload formatting
---

The payload is the actual data provided in a REST message, the payload does not include the overhead data. That means it is not either the headers or the envelope.

Both the *requests* and the *responses* can have a payload. 

For example, in operations such as GET and DELETE, it does not make sense, because there should not be content in the payload. On the other hand, operations like PUT or POST usually contains a payload with data.
Most of the responses may contain a payload, for responses with data content and for providing extra information about the success (or not) of the operation.

There are many formats as payload, the most used are **JSON** and **XML** though. The structure for a payload depends on the information that is represented on it. It will not be the same for an item creation, error content response, successful message, etc.

**Respecting status codes** - It's a bad practice to send a response with status 200, and return in the payload the detail that the response was not successful, with a particular messages result format for our API. The status code for a response must be used to define how was it, don't use the payload to specify the nature of the response. The payload should be used in that case to specify the detail of the operation result.

Sometimes, our API can be prepared to return the responses in xml or json format, the client should specify the format required. There are two ways to define the format of the response expected:
* Accept header: Indicating in the _Accept_ http header what are the contents types accepted. The request put _application/xml_ or _application/json_ to ask for a xml or json response format.
* Extension: other way to specify the response format is indicating the extension on the resource. For example, GET _/api/resource.xml?param=value_ or _/api/resource.json?param=value_.


## Filters

There are several ways to filter the resources of a REST Api. However is a good practice to design an API with the next four features

### Filtering

Avoid use one unique parameter for filtering all fields, is a much better approach to use one parameter for each field to filter. Use multiple values separted by comma if you need to filter a resource by multiple field values.

---
```html
GET /campaigns?status=computed&provider=CVIP,BBVA
```
---

### Sorting

Generic parameter "sort" can be used to describe sorting rules. Allow sort by multiple fields with the use of a list of fields separated by comma. Use - sign before fields to sort in descent order and no sign to sort in ascending order.

---
```html
GET /campaigns?sort=last_update,-status
```
---

### Field Selection

Sometimes API consumers don't need all attributes of a resource. Is a good practice give the consumer the ability to choose returned fields. This will improve API's performance and reduce the network bandwidth.

---
```html
GET /campaigns?fields=id,status,name
```
---

### Searching

Define search as a sub-resorce for your collection. Use generic query parameter like "q" to perform a full text search over your resources and return the search result in the same format as a normal list result. In order to make more complex searches allow the use of full text search operators like + - or "/ .

---
```html
GET /campaigns/search?q=PAYPAL-BBVA
```

---


## Pagination

The right way to include pagination details today is using the ***Link header*** introduced by [RFC 5988](http://tools.ietf.org/html/rfc5988#page-6) 

An API that uses the Link header can return a set of ready-made links so the API consumer doesn't have to construct links themselves. 

---
```html
Link: <https://www.beeva.com/sample/api/v1/cars?offset=15&limit=5>; rel="next",
<https://www.beeva.com/sample/api/v1/cars?offset=50&limit=3>; rel="last",
<https://www.beeva.com/sample/api/v1/cars?offset=0&limit=5>; rel="first",
<https://www.beeva.com/sample/api/v1/cars?offset=5&limit=5>; rel="prev"
```
---

|Name      |  Description |
| ------------- | -------------|
|next      | The link relation for the immediate next page of results.  |
|last      | The link relation for the last page of results.  |
|first      | The link relation for the first page of results.  |
|prev      | The link relation for the immediate previous page of results.  |


But this isn't a complete solution as many APIs do like to return the additional pagination information, like a count of the total number of available results. An API that requires sending a count can use a custom HTTP header like ***X-Total-Count.***

On the other hand, in order to indicate the page that we want to visualized and amount of data per page, we should use some parameters in the rest calling. There are some kind of pagination-based [(you cand find some of them here)](https://developers.facebook.com/docs/graph-api/using-graph-api/v2.5#paging), and these parameters had been define by pagination-type based. 

Anyway whatever pagination-based you choose, there must always be a parameter that indicates  the number of individual objects that are returned in each page (usaully is *limit*) and another one that indicates current page (like *page* , *page_number*, *offset*...)


Sometimes, at Beeva projects, we use a link node in the responses instead of use de link header  to paginate. See the example below:

---
```javascript
{
  "result": {
    "code": 206,
    "info": "Partial Content"
  },
  "paging": {
    "page_size": 3,
    "links": {
      "first": {
        "href": "https://www.beeva.com/sample/api/v1/cars?offset=0&limit=5"
      },
      "prev": {
        "href": "https://www.beeva.com/sample/api/v1/cars?offset=5&limit=5"
      },
      "next": {
        "href": "https://www.beeva.com/sample/api/v1/cars?offset=15&limit=5"
      },
      "last": {
        "href": "https://www.beeva.com/sample/api/v1/cars?offset=50&limit=3"
      }
    }
  },
  "data":  [
      {
        "date": "201401",
        "avg": 46.38
      },
      {
        "date": "201402",
        "avg": 45.66
      },
      {
        "date": "201403",
        "avg": 48.6
      }
    ]
}
```
---

## HATEOAS
---

## API Versioning
---

## API throughput restrictions
---
For performance reasons and to ensure a homogeneous response times APIs, it is good practice to limit the consumption of APIs. This limitation can be performed based on many factors:

* **Limit requests in a time slot for an authenticated user.**. Such limitations are usually carried out in public APIs to control abusive access to the APIs. There are several approaches such as restricting the number of day / month requests for authenticated users.
* **Limit requests for public / private consumption API depending on the authenticated user profile**. Usually public APIs have limited consumption, always with the concept of ensuring homogeneous consumption of all users allowing resizes infrastructure in stages. Now another factor to consider, the payment appears APIs. If someone pays for higher demand requests, we can not make this service affects the service consumer of APIs, so normally corresponding changes will be made in infrastructure to ensure the number of requests the client demands, and there is a unique routing requests asigned to the user profile.

To manage the rate of requests are often used the following headers in responses of each request:

* **X-Rate-Limit-Limit**: The number of allowed requests in the current period
* **X-Rate-Limit-Remaining**: The number of remaining requests in the current period
* **X-Rate-Limit-Reset**: The number of seconds left in the current period. It is necessary to clarify at this point that should not be confused with a timestamp, you should be the seconds remaining to avoid problems with time zones.

As we can see in the following [link](http://stackoverflow.com/questions/16022624/examples-of-http-api-rate-limiting-http-response-headers), There are multiple APIs that use these headers (and sometimes more), to inform the user of the limits.

For ending this section, when the request limit is reached, the response will return this code status: ***HTTP - 429 Too Many Requests*** as indicated in the [RFC 6585](http://tools.ietf.org/html/rfc6585#section-4)


## OAuth
---

### Introduction

The OAuth authorization framework enables a third-party application to obtain limited access to an HTTP service, either on behalf of a resource owner by orchestrating an approval interaction between the resource owner and the HTTP service, or by allowing the third-party application to obtain access on its own behalf.

This is a very brief introduction, please refer to [OAuth RFC 6749](https://tools.ietf.org/html/rfc6749) for a detailed documentation.

### Roles

It is very important to know the involved roles for the sake of this section understanding:

OAuth defines four roles:

   * resource owner : An entity capable of granting access to a protected resource. When the resource owner is a person, it is referred to as an end-user.
   * resource server : The server hosting the protected resources, capable of accepting and responding to protected resource requests using access tokens.
   * client : An application making protected resource requests on behalf of the resource owner and with its authorization.  The term "client" does not imply any particular implementation characteristics (e.g., whether the application executes on a server, a desktop, or other devices).
   * authorization server : The server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization. The authorization server may be the same server as the resource server or a separate entity. A single authorization server may issue access tokens accepted by multiple resource servers.

### OAuth Authentication Flow

The image below describes the authentication flow (usually referred as OAuth handshake or negotiation).

![OAuth Authentication Flow](static/oauth_flow.png "OAuth Protocol Flow")

We can observe 3 separated blocks in this negotiation:

1. **Authorization request to access protected resources from a resource owner**, there are several granting types to use, see references for further details

> We recommend to delegate this authorization process to an authorization server, but it could be handled directly by the resource owner

2. **Access token request**, using the authorization grant obtained in the previous step the client obtains a valid OAuth access token
3. **Retrieval of protected resources**, using OAuth access token obtained in previous step

This scenario can be referred as **3-legged OAuth**. There is a special case when resource owner and client is the same entity, this is called **2-legged OAuth** because there is no need of authorization request from resource owner.

### Scopes

Access tokens are associated to a set of scopes that represent permissions on how and which resources are available for a given token. We recommend to carefully define a rich set of scopes that enable a fine grained set of permissions to restrict client's access and operations to protected resources.

### Token expiration and refresh

Access tokens should have an expiration time defined. It is not a good practice to allow a token to last forever. If a client need to extend the expiration time, a "refresh token" endpoint should be available. In this case, in step 2 of protocol flow a refresh token is provided along with the access token. This refresh token should be used by the client for refreshing the expiration time of a token. See RFC for further details.

### Caching access tokens

A good practice to avoid an unnecesary traffic overhead to the authorization server is to enable caching in clients. This access token will not change until expires.

### Use of TLS/SSL

Client credentials should never travel as plain text without using SSL on requests. This way credentials are protected from eavesdropping and man-in-the-middle attacks.

However, even with the use of TLS/SSL credentials could be sent to the wrong server using OAuth 2.0 - either by misconfiguration or because the server has been compromised. If this is critical for us, maybe the credentials should not travel as plain text, but signed. This sacrifices complexity over protocol requests for a higher security.

### Examples

Below is a list of sample implementations of OAuth 2.0:

* [Google APIs](https://developers.google.com/identity/protocols/OAuth2)
* [Twitter APIs](https://dev.twitter.com/oauth/overview/introduction)

## Errors
---

## Status and Health endpoints
---

A good practice for you REST API is to reserve a couple of endpoints for checking the status and health/integrity of the API.

### Status endpoint

The status endpoint allows third party applications to check if your API is UP or DOWN.

A good endpoint could be **/status**.

The convention for this endpoint is to return a 200 status code response with a very simple response. For example:

```json
{
    "status":"UP"
}
```

Any other response status should be interpreted as an outline API.

### Health endpoint

The health endpoint goes a step further and it does not only informs about the availability of the API but also about its integrity and health.

A good endpoint could be **/health**.

The response in case that our API is healthy could be very similar to the one returned by the status endpoint, a 200 response with the following body:

```json
{
    "health" : "OK"
}
```

We should check the status of every needed sub-component for our API to work correctly. For example, we could check the status of any databases or external services.

In case we need a more verbose response, we could enable a second endpoint whose response could be more detailed. For example, **/health/systems** could return a 200 response with the following body:

```json
{
    "health" : {
        "subsystem A" : "OK",
        "subsystem B" : "KO",
        "subsystem C" : "OK",
        "subsystem D" : "OK"
    }
}
```

This endpoint **should not be published to third party applications** because this information is typically needed for internal development or architecture issues.

### References
---

* [OAuth RFC 6749] (https://tools.ietf.org/html/rfc6749) OAuth RFC 6749 defined by IETF

___

[BEEVA](http://www.beeva.com) | 2015
