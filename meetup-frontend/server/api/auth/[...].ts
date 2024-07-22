import { NuxtAuthHandler } from '#auth'

export default NuxtAuthHandler({
    providers: [
        {
            id: "springAuth",
            name: "SpringAuth",
            type: "oauth",
            wellKnown: "http://localhost:8080/.well-known/openid-configuration",
            authorization: { params: { scope: "openid" } },
            idToken: true,
            checks: ["pkce"],//, "state"],
            clientId: "client",
            clientSecret: "secret",
            profile(profile) {
                return {
                    id: profile.sub,
                    name: profile.name,
                    email: profile.email,
                    image: profile.picture,
                }
            },
        },
    ],
    callbacks: {
        jwt: async ({ token, user, account }) => {
            if (account && account.access_token) {
                token.accessToken = account.access_token; // <-- adding the access_token here
                token.refreshToken = account.refresh_token;
            }

            const userResponse = await $fetch("http://localhost:8080/userinfo");

            const userId = await $fetch("http://localhost:9001/api/meetings/views/userId", {
                headers: {
                    Authorization: `Bearer ${token.accessToken}`
                }
            })

            token.userId = userId

            token.data = userResponse;

            return token;
        },

        async session({ session, user, token }) {
            session.token = token;

            return session;
        },
    }
})