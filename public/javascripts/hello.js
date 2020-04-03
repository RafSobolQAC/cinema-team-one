function sideClose() {
  document.getElementById("mySidebar").style.display = "none";
}

function sideOpen() {
  if (document.getElementById("mySidebar").style.display === "block") {
    sideClose();
  } else {
    document.getElementById("mySidebar").style.display = "block";
  }
}


window.onscroll = function() {footerStick()};
var footer = document.getElementById("myFooter");
var sticky = footer.offsetHeight;
function footerStick() {
  if (window.pageYOffset < sticky) {
    footer.classList.add("sticky");
  } else {
    footer.classList.remove("sticky");
  }
}