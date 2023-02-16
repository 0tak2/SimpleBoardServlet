<template>
    <div style="display: none;">Login Check</div>
</template>

<script>
export default {
    created() {
        if (this.$store.state.userID === '' ||
            this.$store.state.userID === null ||
            this.$store.state.userName === '' ||
            this.$store.state.userName === null) {
                console.log("[LoginCheck] Not Logined");
                this.$router.push({ name: 'login'});
        }
    }
}
</script>

<style>

</style>