package com.ppyy.ppweatherplus.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class WeatherInfoResponse implements Serializable {
    /**
     * indexes : [{"ext":{"icon":"http://static.etouch.cn/icon/chenlian.png","statsKey":"-1055"},"valueV2":"较不宜晨练","name":"晨练指数","value":"较不宜","desc":"有降水，较不宜晨练，室外锻炼请携带雨具。建议年老体弱人群适当减少晨练时间。"},{"ext":{"icon":"http://static.etouch.cn/icon/tianqi.png","statsKey":"-1053"},"valueV2":"天气炎热","name":"穿衣指数","value":"炎热","desc":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"},{"ext":{"icon":"http://static.etouch.cn/icon/shushidu.png","statsKey":"-1059"},"valueV2":"很不舒适","name":"舒适度指数","value":"很不舒适","desc":"白天虽然天气以阴为主，但由于天热，加上湿度较大，您会感到很闷热，很不舒适。"},{"ext":{"icon":"http://static.etouch.cn/icon/ganmao.png","statsKey":"-1056"},"valueV2":"少发感冒","name":"感冒指数","value":"少发","desc":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},{"ext":{"icon":"http://static.etouch.cn/icon/ziwaixian.png","statsKey":"-1054"},"valueV2":"紫外线中等","name":"紫外线强度指数","link":"http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009","value":"中等","desc":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},{"ext":{"icon":"http://static.etouch.cn/icon/lvyou.png","statsKey":"-1057"},"valueV2":"一般旅游","name":"旅游指数","link":"http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009","value":"一般","desc":"有降水，气温较高，还好有微风伴您一路同行。外出旅游请注意防暑降温并携带雨具。"},{"ext":{"icon":"http://static.etouch.cn/icon/xiche.png","statsKey":"-1052"},"valueV2":"不宜洗车","name":"洗车指数","link":"http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009","value":"不宜","desc":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},{"ext":{"icon":"http://static.etouch.cn/icon/liangshai.png","statsKey":"-1058"},"valueV2":"不太适宜晾晒","name":"晾晒指数","link":"http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009","value":"不太适宜","desc":"有降水，可能会淋湿晾晒的衣物，不太适宜晾晒。请随时注意天气变化。"},{"ext":{"icon":"","statsKey":""},"valueV2":"不宜钓鱼","name":"钓鱼指数","value":"不宜","desc":"天气太热，不适合垂钓。"},{"ext":{"icon":"","statsKey":""},"valueV2":"防脱水化妆","name":"化妆指数","value":"防脱水","desc":"天气较热，易出汗，建议使用防脱水化妆品，少用粉底和胭脂，经常补粉。"},{"ext":{"icon":"","statsKey":""},"valueV2":"较不宜运动","name":"运动指数","value":"较不宜","desc":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"},{"ext":{"icon":"","statsKey":""},"valueV2":"带伞","name":"雨伞指数","value":"带伞","desc":"有降水，如果您要短时间外出的话可不必带雨伞。"},{"ext":{"icon":"","statsKey":""},"valueV2":"不适宜约会","name":"约会指数","value":"不适宜","desc":"风卷起热浪袭来，外出约会可能会因为降水败兴而归，男士请别约美眉逛街。"}]
     * meta : {"circle_count":9959,"post_id":"501369","citykey":"101210114","city":"滨江区","upper":"杭州市","html_url":"http://yun.rili.cn/tianqi/index.html?city=101210114","wcity":["w_101210114"],"up_time":"09:28","post_count":2559,"status":1000,"desc":"看看离你最近的历友在哪里"}
     * weatherUrls : {"w_life_index_more":"http://m.weathercn.com/livingindex.do?language=zh-cn&smartid=101210101&partner=1000001009#Indices","w_forecast_90":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&partner=1000001009","w_gradual_hour":""}
     * forecast15 : [{"date":"20170817","sunrise":"05:26","high":35,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=0&partner=1000001009","low":26,"sunset":"18:40","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":47,"forecastAirUrl":"","day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}},{"date":"20170818","sunrise":"05:27","high":36,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=1&partner=1000001009","low":26,"sunset":"18:39","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"绵绵的云朵，形状千变万化"},"aqi":26,"forecastAirUrl":"","day":{"wthr":"阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144700.1717.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206922.4158.jpg","wp":"<3级","type":3,"wd":"西南风","notice":"突如其来的雨，总叫让人措手不及"}},{"date":"20170819","sunrise":"05:28","high":36,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=2&partner=1000001009","low":27,"sunset":"18:38","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":84,"forecastAirUrl":"","day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"东南风","notice":"雷雨闪电时，应切断电器电源，以免损坏"}},{"date":"20170820","sunrise":"05:28","high":35,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=3&partner=1000001009","low":27,"sunset":"18:37","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"悠悠的云里有淡淡的诗"},"aqi":94,"forecastAirUrl":"","day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"南风","notice":"空旷场地不要使用有金属尖端的雨伞"}},{"date":"20170821","sunrise":"05:29","high":35,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=4&partner=1000001009","low":27,"sunset":"18:36","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"绵绵的云朵，形状千变万化"},"aqi":83,"forecastAirUrl":"","day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"南风","notice":"雷雨闪电时，应切断电器电源，以免损坏"}},{"date":"20170822","sunrise":"05:29","high":37,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=5&partner=1000001009","low":28,"sunset":"18:34","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":80,"forecastAirUrl":"","day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"南风","notice":"悠悠的云里有淡淡的诗"}},{"date":"20170823","sunrise":"05:30","high":38,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=6&partner=1000001009","low":28,"sunset":"18:33","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"悠悠的云里有淡淡的诗"},"aqi":54,"forecastAirUrl":"","day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"南风","notice":"今日多云，骑上单车去看看世界吧"}},{"date":"20170824","sunrise":"05:30","high":37,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=7&partner=1000001009","low":28,"sunset":"18:32","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"今日多云，骑上单车去看看世界吧"},"day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"绵绵的云朵，形状千变万化"}},{"date":"20170825","sunrise":"05:31","high":34,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=8&partner=1000001009","low":27,"sunset":"18:31","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"东南风","notice":"下雨了不要紧，撑伞挡挡就行"},"day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"悠悠的云里有淡淡的诗"}},{"date":"20170826","sunrise":"05:32","high":34,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=9&partner=1000001009","low":26,"sunset":"18:30","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"今日多云，骑上单车去看看世界吧"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"东南风","notice":"外出时请注意关好门窗，防止雨水飘入"}},{"date":"20170827","sunrise":"05:32","high":33,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=10&partner=1000001009","low":23,"sunset":"18:29","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"北风","notice":"外出时请注意关好门窗，防止雨水飘入"},"day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"东北风","notice":"今日多云，骑上单车去看看世界吧"}},{"date":"20170828","sunrise":"05:33","high":26,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=11&partner=1000001009","low":22,"sunset":"18:28","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"北风","notice":"外出时请注意关好门窗，防止雨水飘入"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"东北风","notice":"雾蒙蒙的雨天，最喜欢一个人听音乐"}},{"date":"20170829","sunrise":"05:33","high":24,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=12&partner=1000001009","low":22,"sunset":"18:27","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"北风","notice":"外出时请注意关好门窗，防止雨水飘入"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"北风","notice":"下雨了不要紧，撑伞挡挡就行"}},{"date":"20170830","sunrise":"05:34","high":29,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=13&partner=1000001009","low":22,"sunset":"18:25","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"北风","notice":"下雨了不要紧，撑伞挡挡就行"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"北风","notice":"雾蒙蒙的雨天，最喜欢一个人听音乐"}},{"date":"20170831","sunrise":"05:34","high":29,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=14&partner=1000001009","low":22,"sunset":"18:24","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"北风","notice":"雾蒙蒙的雨天，最喜欢一个人听音乐"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"北风","notice":"雾蒙蒙的雨天，最喜欢一个人听音乐"}},{"date":"20170901","sunrise":"05:35","high":23,"forecastUrl":"http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=15&partner=1000001009","low":19,"sunset":"18:23","night":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145732.2823.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888829.8995.jpg","wp":"<3级","type":8,"wd":"西北风","notice":"外出时请注意关好门窗，防止雨水飘入"},"day":{"wthr":"小雨","bgPic":"http://static.etouch.cn/imgs/upload/1501145725.1733.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206934.2691.jpg","wp":"<3级","type":8,"wd":"西北风","notice":"外出时请注意关好门窗，防止雨水飘入"}}]
     * forecast : [{"date":"20170817","sunrise":"05:26","high":35,"low":26,"sunset":"18:40","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":47,"day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}},{"date":"20170818","sunrise":"05:27","high":36,"low":26,"sunset":"18:39","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"绵绵的云朵，形状千变万化"},"aqi":26,"day":{"wthr":"阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144700.1717.jpg","smPic":"http://static.etouch.cn/imgs/upload/1494206922.4158.jpg","wp":"<3级","type":3,"wd":"西南风","notice":"突如其来的雨，总叫让人措手不及"}},{"date":"20170819","sunrise":"05:28","high":36,"low":27,"sunset":"18:38","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"东南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":84,"day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"东南风","notice":"雷雨闪电时，应切断电器电源，以免损坏"}},{"date":"20170820","sunrise":"05:28","high":35,"low":27,"sunset":"18:37","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"悠悠的云里有淡淡的诗"},"aqi":94,"day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"南风","notice":"空旷场地不要使用有金属尖端的雨伞"}},{"date":"20170821","sunrise":"05:29","high":35,"low":27,"sunset":"18:36","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"绵绵的云朵，形状千变万化"},"aqi":83,"day":{"wthr":"雷阵雨","bgPic":"http://static.etouch.cn/imgs/upload/1501144715.4013.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888325.8533.jpg","wp":"<3级","type":4,"wd":"南风","notice":"雷雨闪电时，应切断电器电源，以免损坏"}},{"date":"20170822","sunrise":"05:29","high":37,"low":28,"sunset":"18:34","night":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"南风","notice":"今日多云，骑上单车去看看世界吧"},"aqi":80,"day":{"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"南风","notice":"悠悠的云里有淡淡的诗"}}]
     * hourfc : [{"wthr":32,"shidu":"59%","hourfcUrl":"","wp":"3-4级","time":"201708181000","type":2,"wd":"西风"},{"wthr":33,"shidu":"54%","hourfcUrl":"","wp":"3-4级","time":"201708181100","type":2,"wd":"西风"},{"wthr":34,"shidu":"48%","hourfcUrl":"","wp":"3-4级","time":"201708181200","type":2,"wd":"西南风"},{"wthr":32,"shidu":"62%","hourfcUrl":"","wp":"3-4级","time":"201708181300","type":3,"wd":"东南风"},{"wthr":36,"shidu":"49%","hourfcUrl":"","wp":"4-5级","time":"201708181400","type":2,"wd":"东南风"},{"wthr":34,"shidu":"58%","hourfcUrl":"","wp":"3-4级","time":"201708181500","type":2,"wd":"东南风"},{"wthr":31,"shidu":"80%","hourfcUrl":"","wp":"3-4级","time":"201708181600","type":3,"wd":"西风"},{"wthr":31,"shidu":"79%","hourfcUrl":"","wp":"4-5级","time":"201708181700","type":2,"wd":"西北风"},{"wthr":30,"shidu":"82%","hourfcUrl":"","wp":"4-5级","time":"201708181800","type":2,"wd":"西风"},{"wthr":28,"shidu":"80%","hourfcUrl":"","wp":"4-5级","time":"201708181900","type":2,"wd":"西南风"},{"wthr":29,"shidu":"78%","hourfcUrl":"","wp":"3-4级","time":"201708182000","type":2,"wd":"西南风"},{"wthr":28,"shidu":"77%","hourfcUrl":"","wp":"3-4级","time":"201708182100","type":2,"wd":"西南风"},{"wthr":28,"shidu":"79%","hourfcUrl":"","wp":"3-4级","time":"201708182200","type":2,"wd":"南风"},{"wthr":28,"shidu":"81%","hourfcUrl":"","wp":"3-4级","time":"201708182300","type":2,"wd":"南风"},{"wthr":28,"shidu":"83%","hourfcUrl":"","wp":"3-4级","time":"201708190000","type":2,"wd":"西南风"},{"wthr":27,"shidu":"85%","hourfcUrl":"","wp":"3-4级","time":"201708190100","type":2,"wd":"西南风"},{"wthr":27,"shidu":"87%","hourfcUrl":"","wp":"3-4级","time":"201708190200","type":2,"wd":"西南风"},{"wthr":27,"shidu":"88%","hourfcUrl":"","wp":"3-4级","time":"201708190300","type":2,"wd":"西南风"},{"wthr":27,"shidu":"88%","hourfcUrl":"","wp":"4-5级","time":"201708190400","type":2,"wd":"西南风"},{"wthr":26,"shidu":"89%","hourfcUrl":"","wp":"4-5级","time":"201708190500","type":2,"wd":"西南风"},{"wthr":28,"shidu":"85%","hourfcUrl":"","wp":"4-5级","time":"201708190600","type":1,"wd":"西南风"},{"wthr":29,"shidu":"81%","hourfcUrl":"","wp":"4-5级","time":"201708190700","type":1,"wd":"东南风"},{"wthr":30,"shidu":"76%","hourfcUrl":"","wp":"4-5级","time":"201708190800","type":1,"wd":"东南风"},{"wthr":32,"shidu":"70%","hourfcUrl":"","wp":"4-5级","time":"201708190900","type":1,"wd":"东南风"}]
     * xianhao : [{"f_date":"20170818","f_number":"5,0","action_type":"webview","item_id":"2631702","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170821","f_number":"1,9","action_type":"webview","item_id":"2631703","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170822","f_number":"2,8","action_type":"webview","item_id":"2631704","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170823","f_number":"3,7","action_type":"webview","item_id":"2631705","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170824","f_number":"4,6","action_type":"webview","item_id":"2631733","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170825","f_number":"5,0","action_type":"webview","item_id":"2631748","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"},{"f_date":"20170828","f_number":"1,9","action_type":"webview","item_id":"2631749","click_url":"http://yun.rili.cn/tianqi/traffic.html?city=101210101","title":"限行"}]
     * source : {"link":"http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009","icon":"http://static.etouch.cn/icon/tianqitong.png","title":"中国天气通"}
     * evn : {"no2":27,"mp":"","pm25":12,"o3":19,"so2":6,"aqi":29,"pm10":28,"suggest":"各类人群可自由活动","time":"09:00:00","co":1,"quality":"优"}
     * observe : {"shidu":"75%","wthr":"多云","temp":27,"night":{"bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg"},"up_time":"09:28","wp":"4级","tigan":"27","type":2,"wd":"西南风","day":{"bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg"}}
     */
    private MetaBean meta;
    private WeatherUrlsBean weatherUrls;
    private SourceBean source;
    private EvnBean evn;
    private ObserveBean observe;
    private List<IndexesBean> indexes;
    private List<Forecast15Bean> forecast15;
    private List<ForecastBean> forecast;
    private List<HourfcBean> hourfc;
    private List<XianhaoBean> xianhao;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public WeatherUrlsBean getWeatherUrls() {
        return weatherUrls;
    }

    public void setWeatherUrls(WeatherUrlsBean weatherUrls) {
        this.weatherUrls = weatherUrls;
    }

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }

    public EvnBean getEvn() {
        return evn;
    }

    public void setEvn(EvnBean evn) {
        this.evn = evn;
    }

    public ObserveBean getObserve() {
        return observe;
    }

    public void setObserve(ObserveBean observe) {
        this.observe = observe;
    }

    public List<IndexesBean> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexesBean> indexes) {
        this.indexes = indexes;
    }

    public List<Forecast15Bean> getForecast15() {
        return forecast15;
    }

    public void setForecast15(List<Forecast15Bean> forecast15) {
        this.forecast15 = forecast15;
    }

    public List<ForecastBean> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastBean> forecast) {
        this.forecast = forecast;
    }

    public List<HourfcBean> getHourfc() {
        return hourfc;
    }

    public void setHourfc(List<HourfcBean> hourfc) {
        this.hourfc = hourfc;
    }

    public List<XianhaoBean> getXianhao() {
        return xianhao;
    }

    public void setXianhao(List<XianhaoBean> xianhao) {
        this.xianhao = xianhao;
    }

    public static class MetaBean {
        /**
         * circle_count : 9959
         * post_id : 501369
         * citykey : 101210114
         * city : 滨江区
         * upper : 杭州市
         * html_url : http://yun.rili.cn/tianqi/index.html?city=101210114
         * wcity : ["w_101210114"]
         * up_time : 09:28
         * post_count : 2559
         * status : 1000
         * desc : 看看离你最近的历友在哪里
         */
        private int circle_count;
        private String post_id;
        private String citykey;
        private String city;
        private String upper;
        private String html_url;
        private String up_time;
        private int post_count;
        private int status;
        private String desc;
        private List<String> wcity;

        public int getCircle_count() {
            return circle_count;
        }

        public void setCircle_count(int circle_count) {
            this.circle_count = circle_count;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getCitykey() {
            return citykey;
        }

        public void setCitykey(String citykey) {
            this.citykey = citykey;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUpper() {
            return upper;
        }

        public void setUpper(String upper) {
            this.upper = upper;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }

        public int getPost_count() {
            return post_count;
        }

        public void setPost_count(int post_count) {
            this.post_count = post_count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getWcity() {
            return wcity;
        }

        public void setWcity(List<String> wcity) {
            this.wcity = wcity;
        }
    }

    public static class WeatherUrlsBean {
        /**
         * w_life_index_more : http://m.weathercn.com/livingindex.do?language=zh-cn&smartid=101210101&partner=1000001009#Indices
         * w_forecast_90 : http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&partner=1000001009
         * w_gradual_hour :
         */

        private String w_life_index_more;
        private String w_forecast_90;
        private String w_gradual_hour;

        public String getW_life_index_more() {
            return w_life_index_more;
        }

        public void setW_life_index_more(String w_life_index_more) {
            this.w_life_index_more = w_life_index_more;
        }

        public String getW_forecast_90() {
            return w_forecast_90;
        }

        public void setW_forecast_90(String w_forecast_90) {
            this.w_forecast_90 = w_forecast_90;
        }

        public String getW_gradual_hour() {
            return w_gradual_hour;
        }

        public void setW_gradual_hour(String w_gradual_hour) {
            this.w_gradual_hour = w_gradual_hour;
        }
    }

    public static class SourceBean {
        /**
         * link : http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009
         * icon : http://static.etouch.cn/icon/tianqitong.png
         * title : 中国天气通
         */

        private String link;
        private String icon;
        private String title;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class EvnBean {
        /**
         * no2 : 27
         * mp :
         * pm25 : 12
         * o3 : 19
         * so2 : 6
         * aqi : 29
         * pm10 : 28
         * suggest : 各类人群可自由活动
         * time : 09:00:00
         * co : 1
         * quality : 优
         */

        private int no2;
        private String mp;
        private int pm25;
        private int o3;
        private int so2;
        private int aqi;
        private int pm10;
        private String suggest;
        private String time;
        private int co;
        private String quality;

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public String getMp() {
            return mp;
        }

        public void setMp(String mp) {
            this.mp = mp;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getO3() {
            return o3;
        }

        public void setO3(int o3) {
            this.o3 = o3;
        }

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCo() {
            return co;
        }

        public void setCo(int co) {
            this.co = co;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }

    public static class ObserveBean {
        /**
         * shidu : 75%
         * wthr : 多云
         * temp : 27
         * night : {"bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg"}
         * up_time : 09:28
         * wp : 4级
         * tigan : 27
         * type : 2
         * wd : 西南风
         * day : {"bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg"}
         */

        private String shidu;
        private String wthr;
        private int temp;
        private NightBean night;
        private String up_time;
        private String wp;
        private String tigan;
        private int type;
        private String wd;
        private DayBean day;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public String getWthr() {
            return wthr;
        }

        public void setWthr(String wthr) {
            this.wthr = wthr;
        }

        public int getTemp() {
            return temp;
        }

        public void setTemp(int temp) {
            this.temp = temp;
        }

        public NightBean getNight() {
            return night;
        }

        public void setNight(NightBean night) {
            this.night = night;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public String getTigan() {
            return tigan;
        }

        public void setTigan(String tigan) {
            this.tigan = tigan;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWd() {
            return wd;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }

        public DayBean getDay() {
            return day;
        }

        public void setDay(DayBean day) {
            this.day = day;
        }

        public static class NightBean {
            /**
             * bgPic : http://static.etouch.cn/imgs/upload/1502951083.9978.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888203.3468.jpg
             */

            private String bgPic;
            private String smPic;

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }
        }

        public static class DayBean {
            /**
             * bgPic : http://static.etouch.cn/imgs/upload/1502951078.1311.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888188.2498.jpg
             */

            private String bgPic;
            private String smPic;

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }
        }
    }

    public static class IndexesBean {
        /**
         * ext : {"icon":"http://static.etouch.cn/icon/chenlian.png","statsKey":"-1055"}
         * valueV2 : 较不宜晨练
         * name : 晨练指数
         * value : 较不宜
         * desc : 有降水，较不宜晨练，室外锻炼请携带雨具。建议年老体弱人群适当减少晨练时间。
         * link : http://m.weathercn.com/index.do?language=zh-cn&smartid=101210114&partner=1000001009
         */
        private ExtBean ext;
        private String valueV2;
        private String name;
        private String value;
        private String desc;
        private String link;
        private int iconRes;

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }

        public ExtBean getExt() {
            return ext;
        }

        public void setExt(ExtBean ext) {
            this.ext = ext;
        }

        public String getValueV2() {
            return valueV2;
        }

        public void setValueV2(String valueV2) {
            this.valueV2 = valueV2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public static class ExtBean {
            /**
             * icon : http://static.etouch.cn/icon/chenlian.png
             * statsKey : -1055
             */

            private String icon;
            private String statsKey;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getStatsKey() {
                return statsKey;
            }

            public void setStatsKey(String statsKey) {
                this.statsKey = statsKey;
            }
        }
    }

    public static class Forecast15Bean {
        /**
         * date : 20170817
         * sunrise : 05:26
         * high : 35
         * forecastUrl : http://m.weathercn.com/daily-weather-forecast.do?language=zh-cn&smartid=101210101&day=0&partner=1000001009
         * low : 26
         * sunset : 18:40
         * night : {"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}
         * aqi : 47
         * forecastAirUrl :
         * day : {"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}
         */
        private String date;
        private String sunrise;
        private int high;
        private String forecastUrl;
        private int low;
        private String sunset;
        private NightBeanX night;
        private int aqi;
        private String forecastAirUrl;
        private DayBeanX day;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public int getHigh() {
            return high;
        }

        public void setHigh(int high) {
            this.high = high;
        }

        public String getForecastUrl() {
            return forecastUrl;
        }

        public void setForecastUrl(String forecastUrl) {
            this.forecastUrl = forecastUrl;
        }

        public int getLow() {
            return low;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public NightBeanX getNight() {
            return night;
        }

        public void setNight(NightBeanX night) {
            this.night = night;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getForecastAirUrl() {
            return forecastAirUrl;
        }

        public void setForecastAirUrl(String forecastAirUrl) {
            this.forecastAirUrl = forecastAirUrl;
        }

        public DayBeanX getDay() {
            return day;
        }

        public void setDay(DayBeanX day) {
            this.day = day;
        }

        public static class NightBeanX {
            /**
             * wthr : 多云
             * bgPic : http://static.etouch.cn/imgs/upload/1502951083.9978.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888203.3468.jpg
             * wp : <3级
             * type : 2
             * wd : 西南风
             * notice : 今日多云，骑上单车去看看世界吧
             */

            private String wthr;
            private String bgPic;
            private String smPic;
            private String wp;
            private int type;
            private String wd;
            private String notice;

            public String getWthr() {
                return wthr;
            }

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class DayBeanX {
            /**
             * wthr : 多云
             * bgPic : http://static.etouch.cn/imgs/upload/1502951078.1311.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888188.2498.jpg
             * wp : <3级
             * type : 2
             * wd : 西南风
             * notice : 今日多云，骑上单车去看看世界吧
             */

            private String wthr;
            private String bgPic;
            private String smPic;
            private String wp;
            private int type;
            private String wd;
            private String notice;

            public String getWthr() {
                return wthr;
            }

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }

    public static class ForecastBean {
        /**
         * date : 20170817
         * sunrise : 05:26
         * high : 35
         * low : 26
         * sunset : 18:40
         * night : {"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951083.9978.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888203.3468.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}
         * aqi : 47
         * day : {"wthr":"多云","bgPic":"http://static.etouch.cn/imgs/upload/1502951078.1311.jpg","smPic":"http://static.etouch.cn/imgs/upload/1493888188.2498.jpg","wp":"<3级","type":2,"wd":"西南风","notice":"今日多云，骑上单车去看看世界吧"}
         */

        private String date;
        private String sunrise;
        private int high;
        private int low;
        private String sunset;
        private NightBeanXX night;
        private int aqi;
        private DayBeanXX day;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public int getHigh() {
            return high;
        }

        public void setHigh(int high) {
            this.high = high;
        }

        public int getLow() {
            return low;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public NightBeanXX getNight() {
            return night;
        }

        public void setNight(NightBeanXX night) {
            this.night = night;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public DayBeanXX getDay() {
            return day;
        }

        public void setDay(DayBeanXX day) {
            this.day = day;
        }

        public static class NightBeanXX {
            /**
             * wthr : 多云
             * bgPic : http://static.etouch.cn/imgs/upload/1502951083.9978.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888203.3468.jpg
             * wp : <3级
             * type : 2
             * wd : 西南风
             * notice : 今日多云，骑上单车去看看世界吧
             */

            private String wthr;
            private String bgPic;
            private String smPic;
            private String wp;
            private int type;
            private String wd;
            private String notice;

            public String getWthr() {
                return wthr;
            }

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class DayBeanXX {
            /**
             * wthr : 多云
             * bgPic : http://static.etouch.cn/imgs/upload/1502951078.1311.jpg
             * smPic : http://static.etouch.cn/imgs/upload/1493888188.2498.jpg
             * wp : <3级
             * type : 2
             * wd : 西南风
             * notice : 今日多云，骑上单车去看看世界吧
             */

            private String wthr;
            private String bgPic;
            private String smPic;
            private String wp;
            private int type;
            private String wd;
            private String notice;

            public String getWthr() {
                return wthr;
            }

            public void setWthr(String wthr) {
                this.wthr = wthr;
            }

            public String getBgPic() {
                return bgPic;
            }

            public void setBgPic(String bgPic) {
                this.bgPic = bgPic;
            }

            public String getSmPic() {
                return smPic;
            }

            public void setSmPic(String smPic) {
                this.smPic = smPic;
            }

            public String getWp() {
                return wp;
            }

            public void setWp(String wp) {
                this.wp = wp;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getWd() {
                return wd;
            }

            public void setWd(String wd) {
                this.wd = wd;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }

    public static class HourfcBean {
        /**
         * wthr : 32
         * shidu : 59%
         * hourfcUrl :
         * wp : 3-4级
         * time : 201708181000
         * type : 2
         * wd : 西风
         */

        private int wthr;
        private String shidu;
        private String hourfcUrl;
        private String wp;
        private String time;
        private int type;
        private String wd;

        public int getWthr() {
            return wthr;
        }

        public void setWthr(int wthr) {
            this.wthr = wthr;
        }

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public String getHourfcUrl() {
            return hourfcUrl;
        }

        public void setHourfcUrl(String hourfcUrl) {
            this.hourfcUrl = hourfcUrl;
        }

        public String getWp() {
            return wp;
        }

        public void setWp(String wp) {
            this.wp = wp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWd() {
            return wd;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }
    }

    public static class XianhaoBean {
        /**
         * f_date : 20170818
         * f_number : 5,0
         * action_type : webview
         * item_id : 2631702
         * click_url : http://yun.rili.cn/tianqi/traffic.html?city=101210101
         * title : 限行
         */

        private String f_date;
        private String f_number;
        private String action_type;
        private String item_id;
        private String click_url;
        private String title;

        public String getF_date() {
            return f_date;
        }

        public void setF_date(String f_date) {
            this.f_date = f_date;
        }

        public String getF_number() {
            return f_number;
        }

        public void setF_number(String f_number) {
            this.f_number = f_number;
        }

        public String getAction_type() {
            return action_type;
        }

        public void setAction_type(String action_type) {
            this.action_type = action_type;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
