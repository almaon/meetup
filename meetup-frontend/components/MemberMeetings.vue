<script setup>
const { getSession } = useAuth()

const userData = await getSession();

const memberMeetings = ref(null)

const fetchMemberMeetings = async () => {
	const accessToken = userData.token.accessToken

	const res = await $fetch('http://localhost:9001/api/meetings/views/memberMeetings/' + userData.token.userId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
	})
	memberMeetings.value = res
}

defineExpose({
	fetchMemberMeetings
})

await fetchMemberMeetings()
</script>

<template>
	<h3>Member Meetings</h3>
	<table v-if="memberMeetings.length > 0">
		<thead>
			<th>Title</th>
			<th>City</th>
			<th>Address</th>
		</thead>

		<tbody @click="$emit('meetingSelected', memberMeetings.meetingId)" class="clickable"
			v-for="meeting in memberMeetings.meeting" :key="meeting.meetingId">
			<td>{{ meeting.title }} </td>
			<td>{{ meeting.meetingLocationCity }} </td>
			<td>{{ meeting.meetingLocationAddress }} </td>
		</tbody>
	</table>
</template>
<style scoped></style>