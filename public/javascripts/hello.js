if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}


function sideOpen() {
  document.getElementById("mySidebar").style.display = "block";
}
function sideClose() {
  document.getElementById("mySidebar").style.display = "none";
}

window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader");
var sticky = header.offsetHeight;

function myFunction() {
  if (window.pageYOffset < sticky) {
    header.classList.add("sticky");
  } else {
    header.classList.remove("sticky");
  }
}