if(!!navigator.setResolution)
    navigator.setResolution(1920,1080);

onload = function refresh() { 
    var img = document.getElementById("webcam"); 
    setInterval(function () { 
        img.src = "java/test.png?" + new Date().getTime();
    }, 100); 
} 


onload2 = function startAnimation() { 
    var frameHeight = 102; 
    var frames = 10; 
    var frame = 0; 
    var div = document.getElementById("animation"); 
    setInterval(function () { 
        var frameOffset = (++frame % frames) * -frameHeight; 
        div.style.backgroundPosition = "0px " + frameOffset + "px"; 
    }, 100); 
} 
