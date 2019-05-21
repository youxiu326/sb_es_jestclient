
#### elasticsearch 索引初始化

```

1.put 请求创建索引

http://localhost:9200/bookIndex

返回:
{
     "acknowledged": true
}

2.put 请求创建bookType与mapping

http://localhost:9200/bookindex
{
	"mappings": {
		"booktype": {
			"properties": {
				"createTime": {
					"type": "date",
					"format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
				},
				"code": {
					"type": "string",
					"index": "not_analyzed"
				},
				"name": {
					"type": "text",
                	"analyzer": "ik_max_word",
            		"search_analyzer": "ik_smart"
				},
				"price": {
					"type": "float"
				},
				"autoOnLineDate": {
					"type": "date",
					"format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
				}
			}
		}
	}
}


3.get 查询
http://localhost:9200/bookindex/booktype/_search?pretty
{
	"query":{
		"match_all":{

		}
	}
}

4. post 高亮 name字段 测试ik分词
http://localhost:9200/bookindex/booktype/_search?pretty
{
    "query" : { "match" : { "name" : "悲惨" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "name" : {}
        }
    }
}

```

```
ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,人,民,共和国,共和,和,国国,国歌”，会穷尽各种可能的组合，适合 Term Query；

ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”，适合 Phrase 查询。

```



###### 组合多查询
###### 最重要的查询 https://www.elastic.co/guide/cn/elasticsearch/guide/current/_most_important_queries.html

```
must
文档 必须 匹配这些条件才能被包含进来。
must_not
文档 必须不 匹配这些条件才能被包含进来。
should
如果满足这些语句中的任意语句，将增加 _score ，否则，无任何影响。它们主要用于修正每个文档的相关性得分。
filter
必须 匹配，但它以不评分、过滤模式来进行。这些语句对评分没有贡献，只是根据过滤标准来排除或包含文档。

```

```
{
    "range": {
        "age": {"gte":20, "lt":30}
    }
}
gt  大于
gte 大于等于
lt  小于
lte 小于等于
```



post 组合查询模板
http://localhost:9200/bookindex/booktype/_search?pretty
{
	"query":{
		"bool":{
			"must":     { },
        	"must_not": { },
        	"should": [ ]
		}
	}
}

```
