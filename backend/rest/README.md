# REST Best Practices
At this point we're going to talk about...

[Replace this logo] ![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Introduction](#rest-introduction)
* [URL construction](#url-construction)
* [Operations over resources](#operations-over-resources)
* [Status codes](#status-codes)
* [Payload formatting](#payload-formatting)
* [Filters](#filters)
* [Pagination](#pagination)
* [HATEOAS](#hateoas)
* [API Versioning](#api-versioning)
* [API throughput restrictions](api-throughput-restrictions)
* [OAuth](#oauth)
* [Errors](#errors)
* [Status and Health endpoints](#status-and-health-endpoints)
* [References](#references)

## Introduction
---

## URL Construction
---

## Operations over resources
---

## Status codes

When you are developing a REST API, a common doubt is what status code use in response.  

### Introduction

One of the most important point, while you are designing an API Rest, is correctly choose how we will inform our customers of the status of their requests. Since one of the main features of the REST services is that they are built on HTTP protocol, the best way to inform the user will be to use HTTP status codes.

It is a good practice to add to the response of REST services a new field "result" which contains HTTP status code number and a more descriptive message related to contexts of the request.

### New Resources

When you are creating new resources, you have to report to client that these resources has been created.

Method: POST (We can also use PUT method intead of POST, but POST it's most recomended for creating resources).

---

|Result 			|Code 	|Response Body 			|
| ----------------------- | ---------- | ------------------------------------- |
|Sucessful created	|201		|Empty					|
|Bad Request		|400		|codeError and description	|
|Invalid credentials	|401		|codeError and description	|

---

### Update Resources

When you are updating existing resources, you have to report to client that these resources has been updated.

Method: PUT (We can also use POST method intead of PUT, but PUT it's most recomended for updating resources).

---

|Result 			|Code 	|Response Body	 					|
| ----------------------- | ----------- | ------------------------------------------------------- |
|Sucessful updated	|200		|Empty or fields of updated resource		|
|Bad Request		|400		|codeError and description recommended	|
|Invalid credentials	|401		|codeError and description recommended	|
|Resource not found|404	|Empty								|

---

### Query a Resource

When you are querying an unique and existing resource, you have to report to client the content of this resource.

Method: GET.

---

|Result 				|Code 	|Response Body 						|
| ------------------------------ | ---------- | ------------------------------------------------------- |
|Resource founded		|200		|Resource with partially or fully data		|
|Resource don't exists	|204		|Empty								|
|Bad Request			|400		|codeError and description recommended	|
|Invalid credentials		|401		|codeError and description recommended	|
|Resource not found	|404		|Empty								|

---

### Query Resource List by pattern or all resources

When you are querying a list of resources by certain pattern, you have to report to client at least a list with some info about these resources.

It's also recommended to return info about pagination like total number of resources in list, number of pages, current page, etc....

Method: GET.

---

|Result 				|Code 	|Response Body 						|
| ------------------------------ | ---------- | ------------------------------------------------------- |
|Resources founded	|200		|Resource list with partial or full data		|
|Resources not founded	|204		|Empty								|
|Resources paginated	|206		|Resource list with pagination info		|
|Bad Request			|400		|codeError and description recommended	|
|Invalid credentials		|401		|codeError and description recommended	|

---

### General Purpose

There are other status codes than have to be taken into account:

---

|Action										|Code 	| Example							|
| -------------------------------------------------------------------- | ---------- | -------------------------------------------------------- |
|Request to non-existent or without sense method	| 405	| PUT over all resource collection			|
|Request to existent resource with invalid headers	| 412	| One header is no correct or field missed	|

---

### Annexed 1: 2XX Success

These status codes are informative and tell the user that his request has been processed correctly.

---

|Code      | Message |  Description |
| ------------- | -------------| ------------|
|200 | OK | Request completed successfully. Used mainly in response to GET methods|
| 201| Created | New resource has been created. Used in response to POST methods |
|202 | Accepted | Request has been accepted but has not been completed yet. It is a response code which is commonly used for asynchronous processing |
|204 | No Content | Request has been completed successfully but the method does not return any information. For example, when a resource exists but it does not have information or in response to a DELETE request |
|206 | Partial Content | Partial response indicates that there are more elements available to return. It is good practice to use the Content-Range header to indicate at what position we are in and how many elements is able to return the service |

---

### Annexed 2: 3XX Redirection

These response codes indicate to the user to do any additional actions to complete his request

---
|Code      | Message |  Description |
| ------------- | -------------| ------------|
| 301 | Moved Permanently | The requested resource has been assigned a new permanent URI and any future references to this resource should use one of the returned URIs. |
| 304 | Not Modified | In HEAD and GET requests indicates that the requested resource has not changed since the last request received. Mostly used in caching systems |

---

### Annexed 3: 4XX Client Error

These response codes are used to tell the client that there are some type of error in his request and he have to fix it before send another request

---
|Code      | Message |  Description |
| ------------- | -------------| ------------|
| 400 | Bad Request | The request could not be understood by the server due to malformed syntax. For example, request parameters in bad format in the case of a GET method or a field of a json that does not validate properly in the case of PUT/POSTS methods |
| 401 | Unauthorized | The request requires user authentication |
| 403 | Forbidden | The requested action cannot be carried out on the specified resource. For example, a DELETE operation on a resource that cannot be deleted | 
| 404 |  Not Found | You cannot perform any operation on the requested resource because server has not found anything matching the Request-URI |
| 405 | Method Now Allowed | Unable to perform the specified action on the requested resource |
| 409 | Conflict | The request cannot be completed because there is a problem with the current state of the resource |
| 410 | Gone | The resource in this endpoint is no longer available. Useful for deprecate older versions of an API |
|429 | Too Many Request | Request has been denied because exceeded of rate limits |

---

### Annexed 4: 5XX Server Error

They are used to inform the user of errors in valid requests

---
|Code      | Message |  Description |
| ------------- | -------------| ------------|
| 500 | Internal Server Error | Generic code indicating an unexpected error |
| 503 | Service Unavailable | The server is currently unable to handle the request due to a temporary overloading or maintenance of the server|







---

## Payload formatting
---

## Filters
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
