<script setup>

</script>

<template>
	<nav>
		<ul id="main-nav" class="nav">
			<li>
				<NuxtLink to="/">Home</NuxtLink>
			</li>
			<li>
				<NuxtLink to="/groups">Meeting Groups</NuxtLink>
			</li>
			<li>
				<NuxtLink to="/meetings">Meetings</NuxtLink>
			</li>
			<li>
				<NuxtLink to="/admin">Admin</NuxtLink>
			</li>
			<!--<li>
				<NuxtLink to="/subscription">Subscription</NuxtLink>
			</li>
			<li>
				<NuxtLink to="/admin" class="featured">Admin</NuxtLink>
			</li>-->
		</ul>
	</nav>
</template>

<style scoped>
.nav {
	margin-top: 10px;
	list-style: none;
	padding-left: 0;
}

.nav li {
	display: inline-block;
	margin-right: 10px;
}

.nav li:last-child {
	margin-right: 0;
}

.nav a {
	color: white;
	background-color: #13a4a4;
	padding: 5px 15px;
	border-radius: 2px;
	text-decoration: none;
}

.nav .featured {
	background-color: orange;
}
</style>