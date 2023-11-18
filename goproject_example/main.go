package main

import (
	"fmt"

	"github.com/nicksnyder/go-i18n/v2/i18n"
)

func sendSms(title, text string) {
	// tipa send
	fmt.Printf("%s\n%s", title, text)
}

func main() {
	sendSmsWithConfirmationCode("ru", "8978")
}

func sendSmsWithConfirmationCode(lang string, confirmationCode string) {
	loc := getLocalizer(lang)

	title := loc.MustLocalize(&i18n.LocalizeConfig{
		MessageID: "sms_sender_name",
	})
	text := loc.MustLocalize(&i18n.LocalizeConfig{
		MessageID: "sms_confirmation_code",
		TemplateData: map[string]string{
			"ConfirmCode": confirmationCode,
		},
	})

	sendSms(title, text)
}
