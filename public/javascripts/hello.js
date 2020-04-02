function sideOpen() {
  document.getElementById("mySidebar").style.display = "block";
}
function sideClose() {
  document.getElementById("mySidebar").style.display = "none";
}

var footer = document.getElementById("myFooter");
var sticky = footer.offsetHeight;
function footerStick() {
  if (window.pageYOffset < sticky) {
    footer.classList.add("sticky");
  } else {
    footer.classList.remove("sticky");
  }
}
window.onscroll = function() {footerStick()};
