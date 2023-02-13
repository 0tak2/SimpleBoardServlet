<template>
    <div style="display: none;">Login Check</div>
</template>

<script>
export default {
    mounted() {
        console.log(this.$store.state.userID);
        console.log(this.$store.state.userName);
        if (this.$store.state.userID === '' ||
            this.$store.state.userName === '') {
                this.$router.push({ name: 'login'});
        }
    }
}
</script>

<style>

</style>