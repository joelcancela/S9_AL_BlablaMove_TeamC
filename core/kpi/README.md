# Core KPI

### Author
__Nikita ROUSSEAU__
### Updated
__11:10 24/01/2019__

## Requirements

- Python 3.6.x
- Pip

### Install Dependencies

```bash
pip install --trusted-host pypi.python.org -r requirements.txt
```

## Deployment

You can start the server in (`development`|`production`) environment.

```bash
$ python3 app.py production
```

## Api Documentation and Usage

The API is self documented using a generated HTML document.

This documentation is available at the root directory of the listening server.

The swagger `YML` document *should* be packed with this project.

By default:
```
http://127.0.0.1:5000/
```
