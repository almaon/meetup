<script setup>
const props = defineProps(['meetingId'])
const { getSession } = useAuth()
const userData = await getSession();
const emit = defineEmits(['meetingUpdated'])

const meeting = ref(null)
const group = ref(null)

const userName = ref(userData.token.sub)

const meetingAttendees = ref(null)

watch(() => props.meetingId, async (newVal, oldVal) => {
	if (newVal) {
		load(newVal)
	}
},
	{
		immediate: true,
		deep: true
	}
);

const load = async (meetingId) => {
	const accessToken = userData.token.accessToken
	const res = await $fetch('http://localhost:9001/api/meetings/views/meetingDetails/' + meetingId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		}
	})
	meeting.value = res

	meetingAttendees.value = await $fetch('http://localhost:9001/api/meetings/views/meetingAttendees/' + meetingId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		}
	})

	group.value = await $fetch('http://localhost:9001/api/meetings/views/meetingGroups/' + meeting.value.meetingGroupId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		}
	})
}

const attend = async () => {
	const accessToken = userData.token.accessToken
	await $fetch('http://localhost:9001/api/meetings/commands/addMeetingAttendee', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
		method: "POST",
		body: {
			meetingId: meeting.value.meetingId,
			guestsNumber: 2
		}
	})

	load(props.meetingId)
	emit('meetingUpdated', meeting.value.meetingId)
}

/*const notAttend = async () => {
	const accessToken = userData.token.accessToken
	await $fetch('http://localhost:9001/api/meetings/commands/addMeetingNotAttendee', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
		method: "POST",
		body: {
			meetingId: meeting.value.meetingId,
		}
	})

	load(props.meetingId)
	emit('meetingUpdated', meeting.value.meetingId)
}*/

const leaveMeeting = async (removingReason) => {
	const accessToken = userData.token.accessToken
	await $fetch('http://localhost:9001/api/meetings/commands/removeMeetingAttendee', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
		method: "POST",
		body: {
			meetingId: meeting.value.meetingId,
			attendeeId: userData.token.userId,
			removingReason
		}
	})

	load(props.meetingId)
	emit('meetingUpdated', meeting.value.meetingId)
}

</script>

<template>
	<div v-if="meeting && group">
		<h3>Meeting Details</h3>
		{{ meeting }}
		<div v-if="group.meetingGroupMembers.map(m => m.memberId).includes(userData.token.userId)">
			<div v-if="!meetingAttendees.attendees.map(m => m.memberId).includes(userData.token.userId)">
				<button @click="attend()">attend</button>
			</div>
			<div v-else>
				<button @click="leaveMeeting('removed by member')">remove</button>
			</div>
		</div>
		<div v-else>
			You are not member of the Group: {{ group.name }}
		</div>
	</div>
</template>

<style scoped></style>