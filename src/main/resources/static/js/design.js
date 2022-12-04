
function work(expId){
    // console.log("id is " + expId);
    // alert(expId);

    // alert("image click is working");
}


function like(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/registeredUser/likePost/'+id);
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("like"+id).innerText = httpRequest.responseText;
    }
    console.log("js worked");

}

function dislike(id){

    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", 'http://localhost:8080/registeredUser/dislikePost/'+id);
    httpRequest.send();

    httpRequest.onload = function (){
        document.getElementById("dislike"+id).innerText = httpRequest.responseText;
    }
    console.log("js worked");

}