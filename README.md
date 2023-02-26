# web_sem07
Основы разработки Веб приложений

### To launch do as follows
1) gradle build (Integration Tests use local PostgresDB) with env variables
   1) COMMAND_HOST=localhost;
   2) COMMAND_PORT=5432;
   3) COMMAND_DB=TestTermPediaCommand;
   4) QUERY_HOST=localhost;
   5) QUERY_PORT=5432;
   6) QUERY_DB=TestTermPediaQuery
2) docker-compose up


ab -n 1000 -c 50 http://localhost:9091/api/v1/terms?term_search_name=oop
## С балансировкой
Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        TermPedia
Server Hostname:        localhost
Server Port:            9091

Document Path:          /api/v1/terms?term_search_name=oop
Document Length:        78 bytes

Concurrency Level:      50
Time taken for tests:   1.613 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      408000 bytes
HTML transferred:       78000 bytes
Requests per second:    620.15 [#/sec] (mean)
Time per request:       80.626 [ms] (mean)
Time per request:       1.613 [ms] (mean, across all concurrent requests)
Transfer rate:          247.09 [Kbytes/sec] received

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.4      0      11
Processing:    13   78  30.0     73     224
Waiting:        0   78  30.1     72     224
Total:         14   78  30.0     73     225

Percentage of the requests served within a certain time (ms)
50%     73
66%     85
75%     93
80%     99
90%    119
95%    136
98%    158
99%    176
100%    225 (longest request)

## Без балансировки

Benchmarking localhost (be patient)
Completed 500 requests
Completed 1000 requests
Completed 1500 requests
Completed 2000 requests
Completed 2500 requests
Completed 3000 requests
Completed 3500 requests
Completed 4000 requests
Completed 4500 requests
Completed 5000 requests
Finished 5000 requests


Server Software:        TermPedia
Server Hostname:        localhost
Server Port:            9091

Document Path:          /api/v1/terms?term_search_name=oop
Document Length:        78 bytes

Concurrency Level:      50
Time taken for tests:   6.054 seconds
Complete requests:      5000
Failed requests:        0
Total transferred:      2040000 bytes
HTML transferred:       390000 bytes
Requests per second:    825.96 [#/sec] (mean)
Time per request:       60.536 [ms] (mean)
Time per request:       1.211 [ms] (mean, across all concurrent requests)
Transfer rate:          329.09 [Kbytes/sec] received

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    0   0.5      0       6
Processing:     0   60  21.3     56     188
Waiting:        0   59  21.2     55     188
Total:          0   60  21.2     56     188

Percentage of the requests served within a certain time (ms)
50%     56
66%     64
75%     69
80%     73
90%     85
95%    102
98%    125
99%    138
100%    188 (longest request)
