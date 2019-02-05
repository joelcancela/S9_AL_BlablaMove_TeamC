FROM python:3.6.5-slim
WORKDIR /app
ADD . /app

RUN apt-get update -y && \
  apt-get install -y libffi-dev build-essential && \
  python -m pip install --upgrade pip && \
  pip install --trusted-host pypi.python.org -r requirements.txt

ENTRYPOINT ["python"]
CMD ["app.py", "production"]