<template>
    <div style="display: none;">Login Check</div>
</template>

<script>
export default {
    created() {
        if (this.$store.state.userID === '' ||
            this.$store.state.userName === '') {
                this.$router.push({ name: 'login'});
        }
    }
}
</script>

<style>

</style>