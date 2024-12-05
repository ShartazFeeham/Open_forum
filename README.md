# MicroForum

## Config server
- URL: http://localhost:8888
- Service-specific config: http://localhost:8888/{service-name}/{profile}
- Example: http://localhost:8888/posts/prod

    Encryption CURL:
    ```shell
    curl --location 'http://localhost:8888/encrypt' \
    --header 'Content-Type: text/plain' \
    --data 'Value to encrypt'
    ```

