## Python Microservice

<!-- ### Built with FastAPI

_To Run:_
`uvicorn main:app --reload` -->

### gRPC Server

_To generate gRPC code from the `.proto` file_:
`python -m grpc_tools.protoc -I=proto --python_out=. --grpc_python_out=. proto/analysis.proto`


_Then, To Run:_
`python main.py`