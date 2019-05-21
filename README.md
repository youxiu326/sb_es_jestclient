
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



###### post 组合查询模板
```

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


##### elasticsearch中term与match区别  https://blog.csdn.net/sxf_123456/article/details/78845437

    match是模糊查询
    term是精确查询


     1.term是代表完全匹配，也就是精确查询，搜索前不会再对搜索词进行分词，
     所以我们的搜索词必须是文档分词集合中的一个,
     这些查询通常用于数字，日期和枚举等结构化数据，而不是【全文本字段】

     2.match
       前面提到match搜索会先对搜索词进行分词，对于最基本的match搜索来说，
       只要搜索词的分词集合中的一个或多个存在于文档中即可


```
term精确搜索

这是因为在对文档建立索引时，会将北京奥运分词为北京，奥运，北京奥运，只要搜索词为这三个之一，
都可以将这篇文章搜索出来，而京奥和北京奥并不在分词集合里，所以无法搜索到该文档。
如果对于某个字段，你想精确匹配，即搜索什么词匹配什么词，类似sql中的=操作，
比如只能通过北京奥运搜索到文档3而不想让北京和奥运也搜索到，
那么，你可以在建立索引阶段指定该字段为"index": "not_analyzed",
此时，elasticsearch将不会对该字段的值进行分词操作，只保留全文字索引。
比如本例子中的tags字段,我在建立索引时设置了"index": "not_analyzed", 搜索时，
不管是指定tags为美，还是国，都无法将第一条结果搜索出来

作者：木鸟飞鱼
链接：https://www.jianshu.com/p/eb30eee13923
来源：简书
```


```
http://localhost:9200/bookindex/booktype/_search?pretty
{
	"query":{
		"bool":{
			"must":     {
				"term":{"name":"悲惨世界"}
			},
        	"must_not": {

        	},
        	"should": [
        		{"range":{"price":{"lte":"30"}}}

        	]
		}
	}
}

or boost

{
	"query":{
		"bool":{
			"must":     {
				"term":{"name":"悲惨世界"}
			},
        	"must_not": {

        	},
        	"should": [
        		{
        			"range":{
        				"price":{"lte":"30","boost": 10.0}
        			}
        		}
        	]
		}
	}
}


______result

{
    "took": 33,
    "timed_out": false,
    "_shards": {
        "total": 5,
        "successful": 5,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": 2,
        "max_score": 1.2531602,
        "hits": [
            {
                "_index": "bookindex",
                "_type": "booktype",
                "_id": "1",
                "_score": 1.2531602,
                "_source": {
                    "id": 1,
                    "createTime": "2019-05-21 13:30:48",
                    "name": "悲惨世界",
                    "code": "001",
                    "price": 20.1,
                    "autoOnLineDate": "2019-05-21 05:30:48"
                }
            },
            {
                "_index": "bookindex",
                "_type": "booktype",
                "_id": "2",
                "_score": 0.26742277,
                "_source": {
                    "id": 2,
                    "createTime": "2019-05-21 13:30:48",
                    "name": "动物世界与悲惨世界",
                    "code": "002",
                    "price": 40.1,
                    "autoOnLineDate": "2019-05-21 05:30:48"
                }
            }
        ]
    }
}

```