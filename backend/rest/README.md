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
---

## Payload formatting
---

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
