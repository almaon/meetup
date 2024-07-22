<script setup>
const { getSession } = useAuth()
const userData = await getSession();

const groupId = ref(null)

const allMeetingGroupsRef = ref(null)
const allMemberMeetingGroupsRef = ref(null)
const memberMeetingGroupProposalsRef = ref(null)



const update = () => {
    allMeetingGroupsRef.value.fetchMeetingGroups()
    allMemberMeetingGroupsRef.value.fetchMemberMeetingGroups()
    memberMeetingGroupProposalsRef.value.fetchMemberMeetingGroupProposals()
}

</script>

<template>
    <h2>Meeting Groups</h2>
    <AllMeetingGroups ref="allMeetingGroupsRef" @group-selected="(id) => groupId = id" :userData="userData" />
    <MemberMeetingGroups ref="allMemberMeetingGroupsRef" @group-selected="(id) => groupId = id" :userData="userData" />
    <MeetingGroup @group-updated="(id) => update()" :groupId="groupId" :userData="userData" />
    <MemberMeetingGroupProposals ref="memberMeetingGroupProposalsRef" :userData="userData" />
    <CreateMeetingGroup @proposal-updated="(id) => update()" />
</template>

<style scoped></style>