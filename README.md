# talkTV40
mvp+retrofit+rxjava+okhttp+romlite项目框架
本人见过不少的mvp结构，各有优劣，但是发现大多数mvp demo都不算是正规的mvp，
他们常常把数据的访问交由presenter处理（虽然问题不大，但是不够正式，把model至于何地），
要不就是在view中直接访问model（这是mvp最大的忌讳）。

这个框架分类严格，数据的访问都交由model处实现处理，presnter负责控制逻辑(调度UI的IView接口和model的数据接口)。

Retrofit的网络请求交由okhttp3去处理，利用okhttp的拦截器处理网络任务的日志拦截和缓存拦截，包括离线缓存和304缓存（如果服务器支持的话）

数据库访问框架使用当今最流行的ormlite，有些人认为应该使用GreenDao，因为他访问速度特别快，但是个人之前从事过后台开发，ormlite支持JDBC连接，Spring以及Android平台，与Hibernate和MyBaties在用法上都有相似之处，入门也快，故选用了它。ps:都是很优秀的框架，何必纠结呢。
总之，值得一看，如果你从中能学到一点知识，还希望给我点个星标。谢谢
