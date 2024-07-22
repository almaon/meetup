<script setup>
const meetingId = ref(null)

const allMeetingsRef = ref(null)
const allMemberMeetingsRef = ref(null)

const update = () => {
	allMeetingsRef.value.fetchMeetings()
	allMemberMeetingsRef.value.fetchMemberMeetings()
}

</script>

<template>
	<h2>Meetings</h2>
	<AllMeetings ref="allMeetingsRef" @meeting-selected="(id) => meetingId = id" />
	<MemberMeetings ref="allMemberMeetingsRef" @meeting-selected="(id) => meetingId = id" />
	<Meeting @meeting-updated="(id) => update()" :meetingId="meetingId" />
</template>

<style scoped></style>