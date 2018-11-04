# CORE API Documentation

## Introduction

Each micro-service of `Core` features a REST-API. It has been created with `Swagger Editor` at `swaggerhub`. This tool is brought to us by `SMARTBEAR`.

Two options are offered to you:
- Installing the editor on your machine
- Using the online hub

In order to start quickly, I suggest sticking to the hub (it is free as long as you are an individual).

## Querying

I suggest using [Postman](https://www.getpostman.com/) for API Querying. It is a simple, graphical tool.

You can `import` the API definition of the project :

1) `File`

2) `Import...`

3) `Choose Files`

4) Pick `swagger.yaml` from the directory of each micro-service project

## Editor Usage (Swagger Editor)

Please **RTFMs** available at :
- [SwaggerHub Editor](https://app.swaggerhub.com/help/ui/editor)
- [Swagger 2.0 Specification](https://swagger.io/docs/specification/2-0/)

## Publication

The *REST-API* documentation must be published at the root level of each micro-service (commonly `http://0.0.0.0:5000/`).

The idea is to publicly expose the capabilities of the API to new users by simply visiting the website at its root.

Once you have finished to design the documentation :

1) *download* the `client` as an `html2` website

2) *extract* files from the downloaded archive `html2-client-generated.zip`

3) *push* and *overwrite* `index.html` file in `templates\swagger`

4) *reload* the webapp

You can also *export*/*import* the `swagger` project *to*/*from* the root directory of each micro-service.

While exporting, select `YAML - Unresolved`.

`Postman` is compatible with *Swagger V2* projects.
