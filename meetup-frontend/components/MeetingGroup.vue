<script setup>
const props = defineProps(['groupId', 'userData'])

const emit = defineEmits(['groupUpdated'])
const errorMessage = ref(null)

const group = ref(null)

const userName = ref(props.userData.token.sub)

watch(() => props.groupId, async (newVal, oldVal) => {
	if (newVal) {
		load(newVal)
	}
},
	{
		immediate: true,
		deep: true
	}
);

const load = async (groupId) => {
	const accessToken = props.userData.token.accessToken
	const res = await $fetch('http://localhost:9001/api/meetings/views/meetingGroups/' + groupId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		}
	})
	group.value = res
}

const joinGroup = async () => {
	const accessToken = props.userData.token.accessToken
	await $fetch('http://localhost:9001/api/meetings/commands/joinToGroup', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
		method: "POST",
		body: {
			meetingGroupId: group.value.meetingGroupId
		}
	})

	load(props.groupId)
	emit('groupUpdated', group.value.meetingGroupId)
}

const leaveGroup = async () => {
	const accessToken = props.userData.token.accessToken

	const { data, error } = await useFetch('http://localhost:9001/api/meetings/commands/leaveMeetingGroup', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
		method: "POST",
		body: {
			meetingGroupId: group.value.meetingGroupId
		}
	})

	if (error.value) {
		errorMessage.value = error.value.data.message
	} else {
		load(props.groupId)
		emit('groupUpdated', group.value.meetingGroupId)
	}
}

</script>

<template>
	<ErrorModal v-if="errorMessage" @ok="errorMessage = null">
		{{ errorMessage }}
	</ErrorModal>
	<div v-if="group">
		<h3>Meeting Group Details</h3>
		<div>Name: {{ group.name }} </div>
		<div>Description: {{ group.description }} </div>
		<div>City: {{ group.locationCity }} </div>
		<div>Member no: {{ group.memberCount }}</div>
		<div>Members: {{ group.meetingGroupMembers.map(m => m.name) }}</div>
		<button v-if="!group.meetingGroupMembers.map(m => m.name).includes(userName)" @click="joinGroup()">join
			group</button>
		<button v-else @click="leaveGroup()">leave group</button>
	</div>
</template>

<style scoped></style>