/**
 * Created by Administrator on 2017/12/18.
 */


function getBlog(event) {
    var blog = document.getElementById("blog");
    // blog.style.background = "blue";
    var str;
    var xmlhttp;
    var event = event||window.event;
    var element = event.currentTarget;
    var blogName = element.innerText;
    blog.innerHTML = null;
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
    }else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if(xmlhttp.readyState==4&&xmlhttp.status==200){
            // alert("1111");
            str = xmlhttp.responseText;
            // alert(str);
            blog.innerHTML = marked(str);
        }

    }
    var path = "md/"+blogName+".md";
    xmlhttp.open("GET",path,true);
    xmlhttp.send();
}
