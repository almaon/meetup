<template>
    <div class="container">
        <slot></slot>
        <button @click="$emit('ok')"></button>
    </div>
</template>