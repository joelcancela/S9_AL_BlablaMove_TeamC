# Core Delivery

### Author
__Nikita ROUSSEAU__
### Updated
__10:00 02/11/2018__

## Requirements

- Python 3.6.x
- Dependencies :
  * flask
  * kafka-python

### Install Dependencies

```bash
pip install --trusted-host pypi.python.org -r requirements.txt
```

## Deployment

You can start the server in (`development`|`production`) environment. Set `FLASK_ENV` according to your needs.

```bash
export FLASK_APP = app.py
export FLASK_ENV = development
export FLASK_DEBUG = 1

$ python3 app.py

[...]
INFO:werkzeug: * Running on http://0.0.0.0:5000/ (Press CTRL+C to quit)
```

## Api Documentation and Usage

The API is self documented using a generated HTML document.

This documentation is available at the root directory of the listening server.

The swagger `YML` document *should* be packed with this project.

By default:
```
http://127.0.0.1:5000/
```
