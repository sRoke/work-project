var query = {
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "brandAppId": "$brandAppId"
                    }
                },
                {
                    "match": {
                        "deleted": $false
                    }
                },
                {
                    "match": {
                        "partnerId": "$partnerId"
                    }
                },
                {
                    "match": {
                        "status": "$status"
                    }
                }
            ],
            should: [
                {"term": {"tag": "c"}},
                {"term": {"tag": "d"}}
            ],

            "filter": [
                {
                    "range": {
                        "num": {
                            "gte": 0
                        }
                    }
                }
            ]
        }
    }
}