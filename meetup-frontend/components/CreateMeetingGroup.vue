<script setup>

const { getSession } = useAuth()
const emit = defineEmits(['proposalUpdated'])
const errorMessage = ref(null)


const name = ref("")
const description = ref("")
const city = ref("")
const country = ref("")
const countries = ref(["PL", "D", "UK"])

const showProposedMessage = ref(false)

const create = async () => {
    const userData = await getSession();
    const accessToken = userData.token.accessToken

    const { data, error } = await useFetch('http://localhost:9001/api/meetings/commands/proposeMeetingGroup', {
        headers: {
            Authorization: `Bearer ${accessToken}`
        },
        method: "POST",
        body: {
            name: name.value,
            description: description.value,
            locationCity: city.value,
            locationCountryCode: country.value
        }
    })

    if (error.value) {
        errorMessage.value = error.value.data.message
    } else {
        showProposedMessage.value = true
        emit('proposalUpdated')
    }
}

const reset = () => {
    showProposedMessage.value = false
    name.value = ""
    description.value = ""
    city.value = ""
    country.value = ""
}

reset()
</script>

<template>
    <h3>Create/Propose Meeting Group</h3>
    <ErrorModal v-if="errorMessage" @ok="errorMessage = null">
        {{ errorMessage }}
    </ErrorModal>
    <div v-if="showProposedMessage">
        <div>{{ name }} proposed</div>
        <button @click="reset()">new Proposal</button>
    </div>
    <form v-else @submit.prevent="create()">
        <div>
            <div>Name: </div>
            <div><input type="text" v-model="name" /></div>
            <div>Description: </div>
            <div><input type="text" v-model="description" /></div>
            <div>City: </div>
            <div><input type="text" v-model="city" /></div>
            <div>
                <select name="Country" v-model="country">
                    <option v-for="c in countries" :key="c" :label="c" :value="c"></option>)}
                </select>
            </div>
            <div><input type="submit" value="Send Proposal" /></div>
        </div>
    </form>
</template>

<style scoped></style>