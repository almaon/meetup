
<script setup>

</script>
<template>
    <div class="container">
        <header class="page-header">
            <h1>Meetup</h1>
            <Menue />
        </header>
        <div class="page-content">
            <slot />
        </div>
    </div>
</template>


<style scoped>
.container {
    max-width: 1080px;
    margin: auto;
}
</style>