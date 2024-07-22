<script setup>
const { userData } = defineProps(['userData'])

const memberMeetingGroupProposals = ref(null)

const fetchMemberMeetingGroupProposals = async () => {
	const accessToken = userData.token.accessToken

	const res = await $fetch('http://localhost:9001/api/meetings/views/all/meetingGroupProposals', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
	})
	memberMeetingGroupProposals.value = res.filter(p => p.proposalUserId === userData.token.userId)
}

defineExpose({
	fetchMemberMeetingGroupProposals
})

await fetchMemberMeetingGroupProposals()
</script>

<template>
	<h3>Member Meeting Group Proposals</h3>
	<table v-if="memberMeetingGroupProposals && memberMeetingGroupProposals.length > 0">
		<thead>
			<tr>
				<th>Name</th>
				<th>City</th>
				<th>Country</th>
				<th>Status</th>
			</tr>
		</thead>

		<tbody>
			<tr v-for="memberMeetingGroupProposal in memberMeetingGroupProposals"
				:key="memberMeetingGroupProposal.meetingGroupPoposalId">
				<td>{{ memberMeetingGroupProposal.name }} </td>
				<td>{{ memberMeetingGroupProposal.locationCity }} </td>
				<td>{{ memberMeetingGroupProposal.locationCountryCode }} </td>
				<td>{{ memberMeetingGroupProposal.status }} {{ memberMeetingGroupProposal.status === 'Rejected' ?
					memberMeetingGroupProposal.rejectReason : '' }} </td>
			</tr>
		</tbody>
	</table>
</template>
<style scoped></style>