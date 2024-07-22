<script setup>
const { userData } = defineProps(['userData'])

const emit = defineEmits(['groupSelected'])

const meetingGroups = ref(null)

const userName = ref(userData.token.sub)

const fetchMeetingGroups = async () => {
	const accessToken = userData.token.accessToken

	const res = await $fetch('http://localhost:9001/api/meetings/views/all/meetingGroups', {
		headers: {
			Authorization: `Bearer ${accessToken}`
		},
	})
	meetingGroups.value = res
}

defineExpose({
	fetchMeetingGroups
})

await fetchMeetingGroups()
</script>

<template>
	<h3>All Meeting Groups</h3>
	<table v-if="meetingGroups && meetingGroups.length > 0">
		<thead>
			<tr>
				<th>Name</th>
				<th>City</th>
				<th>Country</th>
				<th>Role</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="meetingGroup in meetingGroups" :key="meetingGroup.meetingGroupId"
				@click="$emit('groupSelected', meetingGroup.meetingGroupId)" class="clickable">
				<td>{{ meetingGroup.name }} </td>
				<td>{{ meetingGroup.locationCity }} </td>
				<td>{{ meetingGroup.locationCountryCode }} </td>
				<td>{{ meetingGroup.meetingGroupMembers.filter(m => m.name === userName).length > 0 ?
					meetingGroup.meetingGroupMembers.filter(m => m.name === userName).map(m =>
						m.role)[0] : '' }} </td>
			</tr>
		</tbody>
	</table>
</template>

<style scoped></style>