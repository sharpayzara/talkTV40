# talkTV40
mvp+retrofit+rxjava+okhttp+romlite项目框架
本人见过不少的mvp结果，各有优劣，但是发现大多数mvp（包括大多数demo）都不算是正规的mvp，
他们常常把数据的访问交由presenter处理（虽然问题不大，但是不够正式，把model至于何地），
要不就是在view中直接访问model（这是mvp最大的忌讳）。

这个框架分类严格，数据的访问都交由model处实现处理，presnter负责控制逻辑(调度UI的IView接口和model的数据接口)。

Retrofit的网络请求交由okhttp3去处理，利用okhttp的拦截器处理网络任务的日志拦截和缓存拦截，包括离线缓存和304缓存（如果服务器支持的话）
