{
	"res": "${(ret?string)!}",
	"msg": "${(msg)!}"<#if file?exists>,
	"file": {
		"id": "${file.id}",
		"name": "${file.fileName!}",
		"path": "${file.filePath}"
	}
	</#if>
}