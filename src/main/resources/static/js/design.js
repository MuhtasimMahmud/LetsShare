
function like(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/registeredUser/likePost/'+id);
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("like"+id).innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);
        checkDislike(id);
    }
}

function dislike(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/registeredUser/dislikePost/'+id);
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
    httpRequest.open("GET", 'http://localhost:8080/getTotalDislikes/'+id);
    httpRequest.send();

    httpRequest.onload = function (){

        document.getElementById("dislike"+id).innerText = httpRequest.responseText;
        console.log(httpRequest.responseText);

    }
}