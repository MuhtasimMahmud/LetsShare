
function like(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/likePost/'+id);
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("like"+id).innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);
        checkDislike(id);
    }
}

function dislike(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/dislikePost/'+id);
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("dislike"+id).innerText = httpRequest.responseText;
        checkLike(id);
    }
}



function checkLike(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/getTotaLikes/'+id);
    httpRequest.send();

    httpRequest.onload = function (){

        document.getElementById("like"+id).innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);

    }
}


function checkDislike(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.send();
    httpRequest.open("GET", 'http://localhost:8080/getTotalDislikes/'+id);

    httpRequest.onload = function (){

        document.getElementById("dislike"+id).innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);

    }
}

function experienceCounter(){
    softwareExpCounter();
    HRExpCounter();
    MarketingExpCounter();
}


function softwareExpCounter(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/SoftwareExperiencesCount');
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("SoftwareExperienceCount").innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);
    }
}



function HRExpCounter(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/HRExperiencesCount');
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("hrExperienceCount").innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);
    }
}



function MarketingExpCounter(){
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/marketingExperiencesCount');
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("MarketingExperienceCount").innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);
    }
}
