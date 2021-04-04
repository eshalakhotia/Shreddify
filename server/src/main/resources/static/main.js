document.ready(function() {
    /*$("#toggle").click(function() {
        $("#neighborsform").toggle();
    });*/
    console.log("in document ready");
    var isChecked = document.getElementById("toggle").checked;
    if(isChecked){
        console.log("Input is checked");
        ('#neighborsform').toggle();
    }
});

function getValue() {
    var isChecked = document.getElementById("toggle").checked;

    if(isChecked){
        console.log("Input is checked");
        document.getElementById("neighborsform").style.display = "none";
        document.getElementById("naiveneighborsform").style.display = "none";
        document.getElementById("radiusform").style.display = "none";
        document.getElementById("naiveradiusform").style.display = "none";

        document.getElementById("neighborsform2").style.display = "block";
        document.getElementById("naiveneighborsform2").style.display = "block";
        document.getElementById("radiusform2").style.display = "block";
        document.getElementById("naiveradiusform2").style.display = "block";
    } else {
        console.log("Input is NOT checked");
        document.getElementById("neighborsform").style.display = "block";
        document.getElementById("naiveneighborsform").style.display = "block"
        document.getElementById("radiusform").style.display = "block";
        document.getElementById("naiveradiusform").style.display = "block";

        document.getElementById("neighborsform2").style.display = "none";
        document.getElementById("naiveneighborsform2").style.display = "none"
        document.getElementById("radiusform2").style.display = "none";
        document.getElementById("naiveradiusform2").style.display = "none";
    }
}

function openCommand(commandName) {
    var i;
    var x = document.getElementsByClassName("command");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(commandName).style.display = "block";
}