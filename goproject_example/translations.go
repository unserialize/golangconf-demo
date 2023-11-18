package main

import (
	"encoding/json"

	"github.com/nicksnyder/go-i18n/v2/i18n"
	"golang.org/x/text/language"
)

func getLocalizer(langName string) *i18n.Localizer {
	bundle := i18n.NewBundle(language.English)
	bundle.RegisterUnmarshalFunc("json", json.Unmarshal)
	bundle.MustLoadMessageFile("translations/en.json")
	bundle.MustLoadMessageFile("translations/ru.json")

	return i18n.NewLocalizer(bundle, langName)
}
