/**
 * Created by Administrator on 2017/12/12.
 */

//欢迎界面
function saying(saying,author){
    this.saying=saying;
    this.author=author;
}

var saying_1 = new saying("真的猛士，敢于直面惨淡的人生，敢于正视淋漓的鲜血。","—— 鲁迅");
var saying_2 = new saying("伟大的成绩和辛勤劳动是成正比例的，有一分劳动就有一分收获，日积月累，从少到多，奇迹就可以创造出来","—— 鲁迅");
var saying_3 = new saying("立志当怀虎胆,求知莫畏羊肠","—— 佚名");

var sayings = [saying_1,saying_2,saying_3];

window.onload = function(){
    var encourage = document.getElementById("saying");
    var author = document.getElementById("author");
    var mandomNumber = parseInt(Math.random()*3);
    encourage.innerHTML = sayings[mandomNumber].saying;
    author.innerHTML = sayings[mandomNumber].author;
}

var timerout = setTimeout(function () {
    location.href = '/Blog/login.jsp';
},3000);


