<script setup>
const { userData } = defineProps(['userData'])

const emit = defineEmits(['groupSelected'])

const memberMeetingGroups = ref(null)

const fetchMemberMeetingGroups = async () => {
	const accessToken = userData.token.accessToken

	const res = await $fetch('http://localhost:9001/api/meetings/views/memberMeetingGroups/' + userData.token.userId, {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
	})
	memberMeetingGroups.value = res
}

defineExpose({
	fetchMemberMeetingGroups
})

await fetchMemberMeetingGroups()
</script>

<template>
	<h3>Member Meeting Groups</h3>
	<table v-if="memberMeetingGroups && memberMeetingGroups.meetingGroups && memberMeetingGroups.meetingGroups.length > 0">
		<thead>
			<tr>
				<th>Name</th>
				<th>City</th>
				<th>Country</th>
				<th>Role</th>
			</tr>
		</thead>

		<tbody>
			<tr @click="$emit('groupSelected', meetingGroup.meetingGroupId)" class="clickable"
				v-for="meetingGroup in memberMeetingGroups.meetingGroups" :key="meetingGroup.meetingGroupId">
				<td>{{ meetingGroup.name }} </td>
				<td>{{ meetingGroup.locationCity }} </td>
				<td>{{ meetingGroup.locationCountryCode }} </td>
				<td>{{ meetingGroup.memberRole }} </td>
			</tr>
		</tbody>
	</table>
</template>
<style scoped></style>