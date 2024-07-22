<script setup>
const { getSession } = useAuth()

const userData = await getSession();

const errorMessage = ref(null)

const meetingGroupProposals = ref(null)
const rejectionReason = ref(null)

const fetchMeetingGroupProposals = async () => {
    const accessToken = userData.token.accessToken

    const res = await $fetch('http://localhost:9001/api/administration/views/all/MeetingGroupProposals', {
        headers: {
            Authorization: `Bearer ${accessToken}`
        },
    })
    meetingGroupProposals.value = res
}

const accept = async (meetingGroupProposalId) => {
    const accessToken = userData.token.accessToken

    const { data, error } = await useFetch('http://localhost:9001/api/administration/commands/AcceptMeetingGroupProposal', {
        headers: {
            Authorization: `Bearer ${accessToken}`
        },
        method: "POST",
        body: {
            meetingGroupProposalId
        }
    })

    if (error.value) {
        errorMessage.value = error.value.data.message
    } else {
        await fetchMeetingGroupProposals()
    }
}

const reject = async (meetingGroupProposalId) => {
    const accessToken = userData.token.accessToken

    const { data, error } = await useFetch('http://localhost:9001/api/administration/commands/RejectMeetingGroupProposal', {
        headers: {
            Authorization: `Bearer ${accessToken}`
        },
        method: "POST",
        body: {
            meetingGroupProposalId,
            decisionRejectReason: rejectionReason.value
        }
    })

    if (error.value) {
        errorMessage.value = error.value.data.message
    } else {
        await fetchMeetingGroupProposals()
    }
}

defineExpose({
    fetchMeetingGroupProposals
})

await fetchMeetingGroupProposals()
</script>

<template>
    <h3>Meeting Group Proposals</h3>
    <ErrorModal v-if="errorMessage" @ok="errorMessage = null">
        {{ errorMessage }}
    </ErrorModal>
    <table v-if="meetingGroupProposals">
        <thead>
            <th>Name</th>
            <th>Description</th>
            <th>City</th>
            <th>Country</th>
            <th>Status</th>
        </thead>

        <tbody v-for="meetingGroupProposal in meetingGroupProposals" :key="meetingGroupProposal.meetingGroupProposalId">
            <td>{{ meetingGroupProposal.name }} </td>
            <td>{{ meetingGroupProposal.description }} </td>
            <td>{{ meetingGroupProposal.locationCity }} </td>
            <td>{{ meetingGroupProposal.locationCountryCode }} </td>
            <td>{{ meetingGroupProposal.status }} <span v-if="meetingGroupProposal.status ===
                'Rejected'"> Reason: </span>{{ meetingGroupProposal.status ===
        'Rejected' ?
        meetingGroupProposal.decisionRejectReason : '' }}
                <button @click="accept(meetingGroupProposal.meetingGroupProposalId)"
                    v-if="meetingGroupProposal.status === 'ToVerify'">Accept</button>
                <button @click="reject(meetingGroupProposal.meetingGroupProposalId)"
                    v-if="meetingGroupProposal.status === 'ToVerify'">Reject</button>
                <input v-if="meetingGroupProposal.status === 'ToVerify'" type="text" v-model="rejectionReason" />
            </td>
        </tbody>
    </table>
</template>
<style scoped></style>