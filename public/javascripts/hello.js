if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}


function sideOpen() {
  if (document.getElementById("mySidebar").style.display === "block") {
    sideClose();
  } else {
    document.getElementById("mySidebar").style.display = "block";
  }
}
function sideClose() {
  document.getElementById("mySidebar").style.display = "none";
}