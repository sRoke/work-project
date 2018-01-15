
# 目的

1. swagger 文档
1. 模拟数据
1. 检查 API 设计

# 检查

```bash

# controller
curl -v http://localhost:10200/
curl -v http://localhost:10200/s
curl -v http://localhost:10200/api/a        # controller
curl -v http://localhost:10200/api/b        # resource
curl -v http://localhost:10200/api/c        # resource


# swagger
http://localhost:10200/webjars/swagger-ui/3.0.19/index.html&url=http://localhost:10200/api/swagger.json
http://localhost:10200/api/swagger.json
http://localhost:10200/api/swagger.yaml
```