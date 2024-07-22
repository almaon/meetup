<script setup>
const { getSession } = useAuth()

const userData = await getSession();

const meetings = ref(null)

async function fetchMeetings() {
	const accessToken = userData.token.accessToken
	const res = await $fetch('http://localhost:9001/api/meetings/views/all/meetings', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		}
	})
	meetings.value = res
}

await fetchMeetings()
</script>

<template>
	<h3>All Meetings</h3>
	<table v-if="meetings.length > 0">
		<thead>
			<th>Title</th>
			<th>City</th>
			<th>Address</th>
		</thead>

		<tbody @click="$emit('meetingSelected', meeting.meetingId)" class="clickable" v-for="meeting in meetings"
			:key="meeting.meetingId">
			<td>{{ meeting.title }} </td>
			<td>{{ meeting.meetingLocationCity }} </td>
			<td>{{ meeting.meetingLocationAddress }} </td>
		</tbody>
	</table>
</template>

<style scoped></style>