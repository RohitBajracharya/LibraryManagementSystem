console.log("script file")

function toggleSidebarMenu() {
	var hamburgerIcon = document.querySelector('#hamburger');
	console.log(hamburgerIcon);
	var sidebar = document.querySelector('.sidebar');
	console.log(sidebar)
	var hamburger = document.querySelector('.hamburger');
	console.log(hamburger)
	var content = document.querySelector('.content')
	sidebar.addEventListener("click", function() {
			hamburger.style.display = 'inline-block'
			sidebar.style.display = 'none'
			content.style.margin = '0'
	})
	hamburger.addEventListener("click", function() {
		
			hamburger.style.display = 'none'
			sidebar.style.display = 'block'
			content.style.margin = ''

	})
}